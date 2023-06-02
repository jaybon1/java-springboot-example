package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                config -> {
                    try {
                        config.antMatchers("/h2/**")
                                .permitAll()
                                .and()
                                .headers().frameOptions().sameOrigin();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        http.csrf().disable();

        http.authorizeHttpRequests(
                config -> config
                        .antMatchers("/auth/login", "/auth/join", "/api/*/auth/**")
                        .permitAll()
                        .antMatchers("/todo")
                        .hasRole("USER") // https://ondolroom.tistory.com/652
                        .anyRequest()
                        .authenticated()
        );

        http.formLogin(
            config -> config
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/login-process")
                    .usernameParameter("id")
                    .passwordParameter("pw")
                    .failureHandler(new CustomAuthFailureHandler())
                    .defaultSuccessUrl("/todo")
                    .permitAll()
        );

        return http.build();
    }
}
