package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.my.config.security.auth.CustomAuthenticationFailureHandler;
import com.example.my.config.security.auth.CustomAuthenticationSuccessHandler;
import com.example.my.config.security.auth.CustomLogoutSuccessHandler;
import com.example.my.config.security.auth.oauth2.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
        private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
        private final CustomOAuth2UserService customOAuth2UserService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

                httpSecurity.csrf(config -> config.disable());

                // httpSecurity.headers(config -> config
                // .frameOptions(frameOptionsConfig -> frameOptionsConfig
                // .disable()
                // )
                // );
                //
                // httpSecurity.authorizeHttpRequests(config -> config
                // .requestMatchers(PathRequest.toH2Console())
                // .permitAll()
                // );

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers("/css/**", "/js/**", "/assets/**", "/favicon.ico")
                                .permitAll()
                                .requestMatchers("/js/admin*.js", "/h2/**", "/temp/**")
                                .hasRole("ADMIN"));

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers("/auth/**", "/api/*/auth/**")
                                .permitAll()
                                .requestMatchers("/admin/**", "/api/*/admin/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated());

                httpSecurity.formLogin(config -> config
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/api/v1/auth/login")
                                .usernameParameter("id")
                                .passwordParameter("password")
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureHandler(customAuthenticationFailureHandler)
                                .permitAll());

                httpSecurity.oauth2Login(config -> config
                                .loginPage("/auth/login")
                                .userInfoEndpoint(endpointConfig -> endpointConfig
                                                .userService(customOAuth2UserService)));

                httpSecurity.logout(config -> config
                                .logoutUrl("/auth/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                .permitAll());

                return httpSecurity.getOrBuild();
        }

}
