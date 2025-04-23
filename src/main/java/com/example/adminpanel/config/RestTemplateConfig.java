package com.example.adminpanel.config;

import com.example.adminpanel.dto.TagDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    private static final String URL = "http://localhost:8080/api/v1/";
    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        LoginDto token = restTemplate.postForObject(URL+"auth/login",new LoginDto(), LoginDto.class); //TODO czy nie bedzie problemu przy dlugo dzialajacej apce


        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, token.getSessionToken());
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}

class LoginDto {
    private String sessionToken;
    private String email;
    private String password;

    public LoginDto() {
        email = "test@test.pl";
        password = "Test1234$";
        //TODO zabezpieczyc dane logowania
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}