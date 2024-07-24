package com.beyond.ticketLink.user.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtMessageType implements MessageType {

    // jwt
    TOKEN_EXPIRED("The JWT token has expired.", HttpStatus.FORBIDDEN)
;
    private final String message;
    private final HttpStatus status;
}
