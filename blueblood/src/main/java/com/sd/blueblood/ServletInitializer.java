package com.sd.blueblood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BluebloodApplication.class);
    }

    public static void main( String[] args )

    {

    SpringApplication.run(BluebloodApplication.class, args);

    }
}