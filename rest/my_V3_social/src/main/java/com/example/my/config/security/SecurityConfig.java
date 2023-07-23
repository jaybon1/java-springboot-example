package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.my.config.security.oauth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                config -> {
                    try {
                        config.antMatchers("/h2/**")
                                .permitAll()
                                .and()
                                .headers(headers -> headers.frameOptions().sameOrigin());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        http.csrf().disable();

        http.authorizeHttpRequests(
                config -> config
                        .antMatchers("/auth/login", "/auth/join", "/api/*/auth/**", "/css/**", "/img/**", "/oauth2/authorization/**", "/login/oauth2/code/**")
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

        http.oauth2Login(
                config -> config
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/todo")
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService)
        );


        return http.build();
    }
}
