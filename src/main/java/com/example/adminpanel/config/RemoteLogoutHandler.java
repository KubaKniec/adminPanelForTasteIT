package com.example.adminpanel.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
class RemoteLogoutHandler implements LogoutHandler {

    private static final String URL = "http://localhost:8080/api/v1/";
    private RestTemplate rest;

    public RemoteLogoutHandler(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public void logout(HttpServletRequest req,
                       HttpServletResponse res,
                       Authentication auth) {

        if (auth != null && auth.getPrincipal() instanceof RemotePrincipal p) {
            HttpHeaders h = new HttpHeaders();
            h.add(HttpHeaders.AUTHORIZATION, p.sessionId());

            try {
                rest.exchange(URL + "auth/logout",
                        HttpMethod.GET,
                        new HttpEntity<>(h),
                        Void.class);
            } catch (Exception ignored) {}
        }
    }
}
