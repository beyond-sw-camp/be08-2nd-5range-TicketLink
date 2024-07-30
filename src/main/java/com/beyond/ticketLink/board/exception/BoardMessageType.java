package com.beyond.ticketLink.board.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardMessageType implements MessageType {

    // board
    BOARD_OPERATION_UNAUTHORIZED("You don't have permission to manipulate board.", HttpStatus.BAD_REQUEST),
    BOARD_NOT_FOUND("No Boards were found for your request.", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus status;

}
