package main.java.com.example.demo_api.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public main.java.com.example.demo_api.service.OpenAIService openAIService(RestTemplate restTemplate) {
        return new main.java.com.example.demo_api.service.OpenAIService(restTemplate);
    }
}