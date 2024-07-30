package com.beyond.ticketLink.reservation.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResMessageType implements MessageType {

    RES_OPERATION_UNAUTHORIZED("You don't have permission to manipulate reservation.", HttpStatus.UNAUTHORIZED),
    RES_OPERATION_DUPLICATE("The reservation of Seat(Ticket) already exists.", HttpStatus.CONFLICT),
    RES_NOT_FOUND("No Reservations were found for your request.", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus status;

}
