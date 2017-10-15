package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PersistentTokenRepository memoryTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder(securityProperties.getPasswordSecret());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage(securityProperties.getFormLoginLoginPage())
            .and()
            .logout()
            .and()
            .rememberMe()
                .tokenRepository(memoryTokenRepository())
                .tokenValiditySeconds(2419200) // 4 weeks
                .key(securityProperties.getRememberMeKey())
            .and()
            .httpBasic()
                .realmName(securityProperties.getRealmName())
            .and()
            .authorizeRequests()
                .regexMatchers("\\/user\\/create\\/?").permitAll()
                .regexMatchers("\\/user\\/delete\\/.*").hasRole("ADMIN")
                .regexMatchers("\\/user(\\/.*)?(\\?.*)?").authenticated()
                .anyRequest().permitAll();
    }
    
    @Autowired
    protected void globalConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(securityProperties.getUsersByUsernameQuery())
                .authoritiesByUsernameQuery(securityProperties.getAuthoritiesByUsernameQuery())
                .passwordEncoder(passwordEncoder());
    }

    @Profile("dev")
    @Component
    @Order(1)
    public static class H2WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private H2ConsoleProperties console;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // H2 console config
            String path = this.console.getPath();
            String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");
            HttpSecurity h2Console = http.antMatcher(antPattern);
            h2Console.formLogin();
            h2Console.authorizeRequests().anyRequest().hasRole("ADMIN");
            h2Console.csrf().disable();
            h2Console.headers().frameOptions().sameOrigin();
        }
    }

    @Component
    @Order(2)
    public static class ManagementWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private ManagementServerProperties management;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String path = management.getContextPath();
            String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");
            HttpSecurity mngtConsole = http.antMatcher(antPattern);
            mngtConsole.formLogin();
            mngtConsole.authorizeRequests().anyRequest().hasRole("ADMIN");
        }
    }
}
