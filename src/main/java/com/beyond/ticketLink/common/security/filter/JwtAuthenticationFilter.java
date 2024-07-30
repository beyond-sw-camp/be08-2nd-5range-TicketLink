package com.beyond.ticketLink.common.security.filter;

import com.beyond.ticketLink.common.security.provider.JwtAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("JwtAuthenticationFilter");
            String accessToken = authorizationHeader.substring(7);
            Authentication authenticate = jwtAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(accessToken, "")
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }

        filterChain.doFilter(request, response);
    }
}
