package com.sd.blueblood;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer{
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
//                .allowedOrigins("http://localhost:8081","http://192.168.1.54:8081")
//                		"http://192.168.1.20","http://192.168.1.54:4200","http://190.186.19.140","http://www.sangretigo.com")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
