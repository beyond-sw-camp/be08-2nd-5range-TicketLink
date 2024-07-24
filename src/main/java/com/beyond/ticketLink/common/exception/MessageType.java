package com.beyond.ticketLink.common.exception;

import org.springframework.http.HttpStatus;

public interface MessageType {
    String getMessage();

    HttpStatus getStatus();

    String name();
}
