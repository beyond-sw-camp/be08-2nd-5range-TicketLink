package com.beyond.ticketLink.user.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserMessageType implements MessageType {

    USER_NOT_FOUND("The user matching the ID doesn't exist.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ID("Duplicate id.", HttpStatus.CONFLICT),
    USER_ROLE_NOT_FOUND("The role matching the name doesn't exist.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD("The passwords don't match.", HttpStatus.BAD_REQUEST)

    ;

    private final String message;
    private final HttpStatus status;
}
