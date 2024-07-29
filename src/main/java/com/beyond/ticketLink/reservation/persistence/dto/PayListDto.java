package com.beyond.ticketLink.reservation.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PayListDto {
    private String payNo;
    private LocalDate resDate;
    private String resNo;
    private String name;
    private String eventDate;
    private String deTime;
    private Integer cnt;
    private Character status;
}
