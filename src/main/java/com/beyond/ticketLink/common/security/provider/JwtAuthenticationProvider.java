package com.beyond.ticketLink.common.security.provider;

import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import com.beyond.ticketLink.user.exception.JwtMessageType;
import com.beyond.ticketLink.user.persistence.redis.repository.ExpiredAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    private final ExpiredAccessTokenRepository expiredAccessTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String accessToken = (String) authentication.getPrincipal();

        if (jwtUtil.isExpired(accessToken) || bannedToken(accessToken)) {
            throw new TicketLinkException(JwtMessageType.TOKEN_EXPIRED);
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

    private boolean bannedToken(String accessToken) {
        return expiredAccessTokenRepository.findById(accessToken).isPresent();
    }
}
