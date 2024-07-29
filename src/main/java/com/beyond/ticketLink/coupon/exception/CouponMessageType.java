package com.beyond.ticketLink.coupon.exception;

import com.beyond.ticketLink.common.exception.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponMessageType implements MessageType {

    INVALID_COUPON_OWNERSHIP("You are not the coupon owner.", HttpStatus.UNAUTHORIZED),
    COUPON_NOT_FOUND("No coupon exists that matches the coupon number.", HttpStatus.NOT_FOUND),
    COUPON_HAS_EXPIRED("", HttpStatus.BAD_REQUEST),
    INVALID_COUPON_OPERATION("Permissions for coupon manipulation do not exist.", HttpStatus.UNAUTHORIZED),


    // 500
    INVALID_COUPON_DISCOUNT_PERCENTAGE("The coupon discount rate is incorrect.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
    private final String message;
    private final HttpStatus status;


}
