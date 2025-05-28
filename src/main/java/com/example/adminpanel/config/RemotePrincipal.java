package com.example.adminpanel.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record RemotePrincipal(String username,
                              String sessionId,
                              Collection<? extends GrantedAuthority> auth) {}

