package com.zakella.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("{'${allowedOrigins}'.split(','}")
    @Value("#{'${allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/api/**");
        allowedOrigins.forEach(corsRegistration::allowedOrigins);
//        corsRegistration
//                .allowedMethods("*");

    }
}
