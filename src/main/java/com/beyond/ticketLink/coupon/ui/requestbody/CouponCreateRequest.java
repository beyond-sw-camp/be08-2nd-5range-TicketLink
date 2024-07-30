package com.beyond.ticketLink.coupon.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequest {

    @NotEmpty
    private String name; // 이름

    @NotNull
    private Integer dcPercent; // 할인율

    @NotEmpty
    private String userNo;

    @NotNull
    private LocalDate expireDate; // 만료일자

}
