package com.spk.web.myfirstws;

import com.spk.web.myfirstws.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MyFirstWsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyFirstWsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyFirstWsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name = "AppProperties")
    public AppProperties appProperties() {
        return new AppProperties();
    }


}
