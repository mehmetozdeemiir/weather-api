package com.project.weatherapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();//artık application context de bir resttemplate nesnesi var injection yapabilirim
    }
}
