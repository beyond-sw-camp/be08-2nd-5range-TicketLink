package com.beyond.ticketLink.reservation.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    private String resNo; // 예약번호, pk
    private LocalDate resDate; // 예약일자
    private Character status;   // 예약상태
    private String ticketNo;
    private String payNo;
}
