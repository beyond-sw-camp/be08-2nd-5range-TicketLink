package com.beyond.ticketLink.common.security.provider;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;
    private final UserService service;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String accessToken = (String) authentication.getPrincipal();
        log.info("JwtProvider");
        log.info("accessToken = {}", accessToken);
        if (jwtUtil.isExpired(accessToken)) {
            throw new TicketLinkException(MessageType.USER_NOT_FOUND);
        }

        String username = jwtUtil.getUsername(accessToken);

        UserDetails userDetails = service.loadUserByUsername(username);
        log.info(userDetails.toString());
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
