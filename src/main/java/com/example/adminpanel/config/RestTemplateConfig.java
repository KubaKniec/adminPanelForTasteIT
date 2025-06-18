package com.example.adminpanel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.getInterceptors().add((req, body, ex) -> {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            if (a != null && a.getPrincipal() instanceof RemotePrincipal p) {
                req.getHeaders().add(HttpHeaders.AUTHORIZATION,
                        p.sessionId());
            }
            return ex.execute(req, body);
        });
        return rt;
    }
}

class LoginDto {
    private String sessionToken;
    private String email;
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
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