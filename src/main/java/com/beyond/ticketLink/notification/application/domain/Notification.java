package com.beyond.ticketLink.notification.application.domain;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;
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

    private Character notiStatus;

    private String payNo; // 외래키

    private PayInfo payInfo;
}
