package com.beyond.ticketLink.user.application.mock;



import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithTicketLinkMockUserSecurityContextFactory.class)
public @interface WithTicketLinkMockUser {
    String userNo() default "DUMMYA";
    String role() default "일반사용자";
}
