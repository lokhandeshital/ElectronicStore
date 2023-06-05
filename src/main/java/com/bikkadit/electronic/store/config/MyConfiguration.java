package com.bikkadit.electronic.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}
