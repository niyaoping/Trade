package com.demo.good.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registAllowOriginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AllowOriginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("AllowOriginFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registLoginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LoginFilter");
        registration.setOrder(2);
        return registration;
    }

}