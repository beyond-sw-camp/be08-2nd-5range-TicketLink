package com.beyond.ticketLink.coupon.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coupon {
    private String couponNo;  // 쿠폰번호, pk
    private String code; // 쿠폰코드
    private String name;  // 이름
    private Integer dcPercent;    // 할인율
    private LocalDate insDate; // 발급일자
    private LocalDate expireDate;    // 만료일자
    private String userNo;     // 사용자번호, fk
}

