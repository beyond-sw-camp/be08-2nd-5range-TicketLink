package com.beyond.ticketLink.event.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    private String ticketNo;    // 티켓번호, pk
    private String seatRate;    // 좌석등급
    private Integer seatNum;     // 좌석번호
    private Integer price;  // 가격
    private String dayEventNo;  // 일자별 행사번호, fk
}
