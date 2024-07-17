package com.beyond.ticketLink.common.security.provider;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String accessToken = (String) authentication.getPrincipal();
        log.info("JwtProvider");
        log.info("accessToken = {}", accessToken);
        if (jwtUtil.isExpired(accessToken)) {
            throw new TicketLinkException(MessageType.USER_NOT_FOUND);
        }

        String userNo = jwtUtil.getUserNo(accessToken);
        String role = jwtUtil.getRole(accessToken);

        return new UsernamePasswordAuthenticationToken(
                userNo,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
