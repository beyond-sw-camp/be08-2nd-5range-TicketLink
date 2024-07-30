package com.beyond.ticketLink.coupon.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CouponCreateDto {
    private String couponNo; // 쿠폰번호
    private String code; // 쿠폰코드
    private String name; // 이름
    private Integer dcPercent; // 할인율
    private LocalDate insDate; // 발급일자
    private LocalDate expireDate; // 만료일자
    private String userNo; // 사용자번호
}
