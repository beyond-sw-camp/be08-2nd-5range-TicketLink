package com.beyond.ticketLink.common.security.config;

import com.beyond.ticketLink.common.security.exception.JwtAccessDeniedHandler;
import com.beyond.ticketLink.common.security.exception.JwtAuthenticationEntryPoint;
import com.beyond.ticketLink.common.security.filter.JwtAuthenticationFilter;
import com.beyond.ticketLink.common.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class TicketLinkSecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        jwtAuthenticationFilter(jwtAuthenticationProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        http.authorizeHttpRequests((registry -> {
            registry.requestMatchers(
                    "/api/v1/user/register",
                    "/api/v1/user/check-duplicate",
                    "/api/v1/user/login",
                    "/api/v1/mail/**"
            ).permitAll();

            registry.requestMatchers("/api/v1/user/logout").hasAnyRole("관리자", "일반사용자");
            registry.requestMatchers("/api/v1/user/**").hasRole("관리자");

            registry.anyRequest().authenticated();
        }));

        http.exceptionHandling(ex -> {
            ex.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            ex.accessDeniedHandler(jwtAccessDeniedHandler);
        });

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "PATCH"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        return new JwtAuthenticationFilter(jwtAuthenticationProvider);
    }

}
