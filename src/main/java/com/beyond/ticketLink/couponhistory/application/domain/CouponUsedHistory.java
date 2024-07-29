package com.beyond.ticketLink.couponhistory.application.domain;

import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsedHistory {
    private String useHistoryNo;
    private Date useDate;
    private Coupon coupon;
    private PayInfo payInfo;
}