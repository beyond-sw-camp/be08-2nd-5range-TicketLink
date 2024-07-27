package com.beyond.ticketLink.notification.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {

    private String notiNo;

    private String message;

    private LocalDate notiDate;

    private Character notiStaus;

    private String payNo; // 외래키
}
