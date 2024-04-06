package ru.cotarius.hibernatecourse.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("reader*/**").hasAnyAuthority("user", "admin")
                        .requestMatchers("book*/**").permitAll()
                        .requestMatchers("issue*/**").hasAuthority("admin")
                        .requestMatchers("swagger-ui/**").hasAuthority("admin")
                        .requestMatchers("v3/**").hasAuthority("admin")
                        .anyRequest().denyAll()
                )
                .formLogin(Customizer.withDefaults())
                .build();

    }
}
