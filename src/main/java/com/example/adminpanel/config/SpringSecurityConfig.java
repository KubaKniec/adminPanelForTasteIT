package com.example.adminpanel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final RemoteAuthProvider remoteAuthProvider;
    private RemoteLogoutHandler remoteLogoutHandler;

    public SpringSecurityConfig(RemoteAuthProvider remoteAuthProvider, RemoteLogoutHandler remoteLogoutHandler) {
        this.remoteAuthProvider = remoteAuthProvider;
        this.remoteLogoutHandler = remoteLogoutHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(remoteAuthProvider)
                .authorizeHttpRequests(requests ->
                        requests.anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(remoteLogoutHandler)
                        .logoutSuccessUrl("/login?logout"));

        return http.build();
    }
}

