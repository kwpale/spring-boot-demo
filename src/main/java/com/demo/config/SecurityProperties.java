package com.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private String usersByUsernameQuery;
    private String authoritiesByUsernameQuery;
    private String realmName;
    private String rememberMeKey;
    private String formLoginLoginPage;
    private String passwordSecret;

    public String getUsersByUsernameQuery() {
        return usersByUsernameQuery;
    }

    public void setUsersByUsernameQuery(String usersByUsernameQuery) {
        this.usersByUsernameQuery = usersByUsernameQuery;
    }

    public String getAuthoritiesByUsernameQuery() {
        return authoritiesByUsernameQuery;
    }

    public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
        this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getRememberMeKey() {
        return rememberMeKey;
    }

    public void setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    public String getFormLoginLoginPage() {
        return formLoginLoginPage;
    }

    public void setFormLoginLoginPage(String formLoginLoginPage) {
        this.formLoginLoginPage = formLoginLoginPage;
    }

    public String getPasswordSecret() {
        return passwordSecret;
    }

    public void setPasswordSecret(String passwordSecret) {
        this.passwordSecret = passwordSecret;
    }
}
