package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
//            Added for H2 dev
            .and()
            .csrf()
                .disable()
            .headers()
                .frameOptions().disable()
//            End for H2 dev
            .and()
            .authorizeRequests()
                .regexMatchers("\\/user\\/create\\/?").permitAll()
                .regexMatchers("\\/user\\/delete\\/.*").hasRole("ADMIN")
                .regexMatchers("\\/user(\\/.*)?(\\?.*)?").authenticated()
                .anyRequest().permitAll();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(securityProperties.getUsersByUsernameQuery())
                .authoritiesByUsernameQuery(securityProperties.getAuthoritiesByUsernameQuery())
                .passwordEncoder(passwordEncoder());
    }
}
