package org.example._7_02.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity sec) {
        sec.formLogin(f -> f.disable());
        sec.csrf(c -> c.disable());
        sec.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        sec.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        return sec.build();
    }
}
