package com.example.adminpanel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RemoteAuthProvider implements AuthenticationProvider {

    private final RestTemplate rest;        // wstrzyknięty z interceptorem (pkt 4)
//    @Value("${remote.base-url}")
//    private String remoteBase;
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

        // 1️⃣  Logowanie do zewnętrznego serwisu -----------------------------
        HttpHeaders hdr = new HttpHeaders();
        hdr.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> body = Map.of("email", username,
                "password", password);

        ResponseEntity<Map> res = rest.exchange(
                remoteBase + "auth/login",
                HttpMethod.POST,
                new HttpEntity<>(body, hdr),
                Map.class);

        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new BadCredentialsException("Remote auth failed");
        }

        // 2️⃣  Wyciągamy sessionID z nagłówka Set-Cookie ----------------------
//        String jsid = res.getHeaders()
//                .getFirst(HttpHeaders.SET_COOKIE);   // "JSESSIONID=abc; Path=/;..."
        String jsid = (String) res.getBody().get("sessionToken");
        jsid = parseSessionId(jsid);// własna utilka

        // 3️⃣  Role (opcjonalnie) --------------------------------------------
//        List<String> roles = fetchRoles(username, jsid);      // GET /api/me
//
//        List<GrantedAuthority> ga = roles.stream()
//                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
//                .toList();

        // 4️⃣  Pakujemy wszystko w Principal  --------------------------------
        RemotePrincipal principal = new RemotePrincipal(username, jsid, new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
    }

    private String parseSessionId(String jsid) {
        System.out.println("otrzymano session token " + jsid);
        return jsid;
    }

    @Override
    public boolean supports(Class<?> c) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(c);
    }
}

