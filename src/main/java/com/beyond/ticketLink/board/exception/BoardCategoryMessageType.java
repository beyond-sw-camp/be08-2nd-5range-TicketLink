package com.beyond.ticketLink.board.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardCategoryMessageType implements MessageType {

    BOARD_CATEGORY_NOT_FOUND("", HttpStatus.NOT_FOUND)

    ;

    private final String message;
    private final HttpStatus status;
}
