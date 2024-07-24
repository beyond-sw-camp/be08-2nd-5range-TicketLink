package com.beyond.ticketLink.reply.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyMessageType implements MessageType {

    REPLY_OPERATION_UNAUTHORIZED("You don't have permission to manipulate comments.", HttpStatus.UNAUTHORIZED),
    REPLY_NOT_FOUND("No comments were found for your request.", HttpStatus.NOT_FOUND)

    ;

    private final String message;
    private final HttpStatus status;
}
