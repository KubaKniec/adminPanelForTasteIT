package com.example.adminpanel.config;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Map;

@Component
public class RemoteAuthProvider implements AuthenticationProvider {
    private final RestTemplate rest;
    private static final String URL = "http://localhost:8080/api/v1/";
    String remoteBase = URL;
    public RemoteAuthProvider(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        HttpHeaders hdr = new HttpHeaders();
        hdr.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> body = Map.of("email", username,
                "password", password);
        ResponseEntity<Map> res = rest.exchange(
                remoteBase + "auth/admin/login",
                HttpMethod.POST,
                new HttpEntity<>(body, hdr),
                Map.class);
        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new BadCredentialsException("Remote auth failed");
        }
        String jsid = (String) res.getBody().get("sessionToken");
        RemotePrincipal principal = new RemotePrincipal(username, jsid, new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
    }


    @Override
    public boolean supports(Class<?> c) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(c);
    }
}

