package com.beyond.ticketLink.common.security.config;

import com.beyond.ticketLink.common.security.exception.JwtAccessDeniedHandler;
import com.beyond.ticketLink.common.security.exception.JwtAuthenticationEntryPoint;
import com.beyond.ticketLink.common.security.filter.JwtAuthenticationFilter;
import com.beyond.ticketLink.common.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    public static final String ADMIN = "관리자";
    public static final String USER = "일반사용자";

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
                    "/api/v1/mail/**",
                    "/api/v1/board-categories"
            ).permitAll();

            // swagger config
            registry.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll();

            // user role config
            registry.requestMatchers(HttpMethod.GET, "/api/v1/user/profile").hasAnyRole(USER, ADMIN);
            registry.requestMatchers(HttpMethod.POST,"/api/v1/user/logout").hasAnyRole(USER, ADMIN);
            registry.requestMatchers(HttpMethod.GET, "/api/v1/user/*").hasRole(ADMIN);

            // board role config
            registry.requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/boards",
                    "/api/v1/boards/*"
            ).permitAll();

            // event role config
            registry.requestMatchers(HttpMethod.GET,"/api/v1/events/**").permitAll();
            registry.requestMatchers("/api/v1/event/**").hasRole(ADMIN);

            // reservation role config
            registry.requestMatchers("/api/v1/res/**").hasAnyRole(USER, ADMIN);
            registry.requestMatchers(HttpMethod.POST, "/api/v1/res/*").hasRole(USER);

            // coupon config
            registry.requestMatchers(HttpMethod.POST, "/api/v1/coupons").hasRole(ADMIN);
            registry.requestMatchers(HttpMethod.PUT, "/api/v1/coupons/**").hasRole(ADMIN);
            registry.requestMatchers(HttpMethod.DELETE, "/api/v1/coupons/**").hasRole(ADMIN);

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
