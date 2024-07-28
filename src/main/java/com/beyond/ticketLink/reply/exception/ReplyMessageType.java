package com.beyond.ticketLink.reply.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyMessageType implements MessageType {

    REPLY_TARGET_BOARD_NOT_FOUND("The board you want to comment on doesn't exist.", HttpStatus.NOT_FOUND),
    GENERATE_REPLY_NO_FAILED("", HttpStatus.INTERNAL_SERVER_ERROR),
    REPLY_OPERATION_UNAUTHORIZED("You don't have permission to manipulate comments.", HttpStatus.UNAUTHORIZED),
    REPLY_NOT_FOUND("No comments were found for your request.", HttpStatus.NOT_FOUND)

    ;

    private final String message;
    private final HttpStatus status;
}
