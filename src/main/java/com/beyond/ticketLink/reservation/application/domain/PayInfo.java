package com.beyond.ticketLink.reservation.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayInfo {
    private String payNo; // 결제번호, pk
    private Character payment;  // 결제수단
    private LocalDate payDate; // 결제완료일자
    private Character status;   // 결제상태
    private Long price; // 가격
    private Long fee;   // 예매수수료
    private Long deliveryCost;  // 배송비
    private Long discount;  // 할인금액
    private Long totalAmt;  // 총 금액
    private String userNo;
    private LocalDate insDate;
    private List<Reservation> reservations;
}
