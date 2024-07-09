package com.beyond.ticketLink.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {

    // common errors
    BAD_REQUEST ("Check API request URL protocol, parameter, etc. for errors", HttpStatus.BAD_REQUEST),
    NOT_FOUND ("No data was found for the server. Please refer  to parameter description.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR ("An error occurred inside the server.", HttpStatus.INTERNAL_SERVER_ERROR);

    // add additional errors

    private final String message;
    private final HttpStatus status;

    MessageType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
