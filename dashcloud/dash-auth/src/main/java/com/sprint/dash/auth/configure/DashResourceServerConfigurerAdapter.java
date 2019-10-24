package com.sprint.dash.auth.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//@Configuration
//@EnableResourceServer
public class DashResourceServerConfigurerAdapter implements ResourceServerConfigurer {

    public DashResourceServerConfigurerAdapter(){}

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) throws Exception {

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().anyRequest().authenticated();
    }
}
