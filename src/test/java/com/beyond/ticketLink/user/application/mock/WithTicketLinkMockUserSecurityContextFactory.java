package com.beyond.ticketLink.user.application.mock;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithTicketLinkMockUserSecurityContextFactory implements WithSecurityContextFactory<WithTicketLinkMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithTicketLinkMockUser annotation) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                annotation.userNo(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + annotation.role()))
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}
