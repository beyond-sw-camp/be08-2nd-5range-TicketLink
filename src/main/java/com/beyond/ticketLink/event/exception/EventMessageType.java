package com.beyond.ticketLink.event.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EventMessageType implements MessageType {

    EVENT_OPERATION_UNAUTHORIZED("You don't have permission to manipulate event.", HttpStatus.UNAUTHORIZED),
    EVENT_NOT_FOUND("No Events were found for your request.", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus status;

}
