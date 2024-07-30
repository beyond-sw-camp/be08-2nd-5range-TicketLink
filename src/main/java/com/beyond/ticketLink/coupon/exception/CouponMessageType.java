package com.beyond.ticketLink.coupon.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponMessageType implements MessageType {

    COUPON_NOT_FOUND("No coupon exists that matches the coupon number.", HttpStatus.NOT_FOUND),
    INVALID_COUPON_OPERATION("Permissions for coupon manipulation do not exist.", HttpStatus.UNAUTHORIZED)

    ;
    private final String message;
    private final HttpStatus status;


}
