package com.cognixia.stagestream.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://stagestreamshirts.s3-website-us-east-1.amazonaws.com")
                .allowedOriginPatterns("http://stagestreamshirts.s3-website-us-east-1.amazonaws.com/*")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}