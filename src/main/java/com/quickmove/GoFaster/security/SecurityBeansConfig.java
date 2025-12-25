package com.quickmove.GoFaster.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityBeansConfig(UserDetailsService customUserDetailsService,
                               PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Use the constructor that takes UserDetailsService
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder); // BCryptPasswordEncoder or any PasswordEncoder
        return provider;
    }
}
