package org.vvspickles.userservice.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/*").permitAll() // Public endpoints
                        .anyRequest().authenticated() // Everything else requires auth
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Optional: disable basic auth
                .formLogin(formLogin -> formLogin.disable()); // Optional: disable form login

        return http.build();
    }
}
