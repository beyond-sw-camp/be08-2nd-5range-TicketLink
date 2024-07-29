package com.beyond.ticketLink.couponhistory.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponHistoryMessageType implements MessageType {

    ;

    private final String message;
    private final HttpStatus status;
}
