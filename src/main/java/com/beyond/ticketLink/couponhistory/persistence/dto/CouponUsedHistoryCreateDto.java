package com.beyond.ticketLink.couponhistory.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsedHistoryCreateDto {
    private String useHistoryNo;
    private Date useDate;
    private String couponNo;
    private String payNo;
}
