package com.beyond.ticketLink.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {

    // common errors
    BAD_REQUEST("Check API request URL protocol, parameter, etc. for errors", HttpStatus.BAD_REQUEST),
    NOT_FOUND("No data was found for the server. Please refer  to parameter description.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("An error occurred inside the server.", HttpStatus.INTERNAL_SERVER_ERROR),

    // jwt
    TOKEN_EXPIRED("The JWT token has expired.", HttpStatus.FORBIDDEN),

    // user
    USER_NOT_FOUND("The user matching the ID doesn't exist.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ID("Duplicate id.", HttpStatus.CONFLICT),
    ARGUMENT_NOT_VALID("The format of the argument passed is invalid.", HttpStatus.BAD_REQUEST),
    USER_ROLE_NOT_FOUND("The role matching the name doesn't exist.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD("The passwords don't match.", HttpStatus.BAD_REQUEST),

    // board
    BOARD_NOT_FOUND("", HttpStatus.NOT_FOUND),

    // boardCategory
    BOARD_CATEGORY_NOT_FOUND("", HttpStatus.NOT_FOUND),

    // email
    VERIFICATION_EXPIRED("Your email verification has expired.", HttpStatus.REQUEST_TIMEOUT),
    VERIFICATION_CODE_INVALID("The verification code doesn't match.", HttpStatus.BAD_REQUEST),
    EMAIL_UNAUTHORIZED("This is an unauthenticated email.", HttpStatus.UNAUTHORIZED),
    ;



    private final String message;
    private final HttpStatus status;

    MessageType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
