package com.beyond.ticketLink.coupon.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CouponUpdateRequest {

        @NotEmpty
        private String name; // 이름

        @NotNull
        private Integer dcPercent; // 할인율

        @NotNull
        private LocalDate expireDate; // 만료일자

        public CouponUpdateRequest(String name, Integer dcPercent, LocalDate expireDate) {
                this.name = name;
                this.dcPercent = dcPercent;
                this.expireDate = expireDate;
        }

        public String getName() {
                return name;
        }

        public Integer getDcPercent() {
                return dcPercent;
        }

        public LocalDate getExpireDate() {
                return expireDate;
        }
}
