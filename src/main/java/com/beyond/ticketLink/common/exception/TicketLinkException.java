package com.beyond.ticketLink.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TicketLinkException extends RuntimeException {
    private final HttpStatus status;
    private final String type;

    public TicketLinkException(MessageType message) {
        super(message.getMessage());
        this.status = message.getStatus();
        this.type = message.name();
    }
}
