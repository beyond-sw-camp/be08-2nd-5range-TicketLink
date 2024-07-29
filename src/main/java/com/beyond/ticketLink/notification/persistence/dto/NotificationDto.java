package com.beyond.ticketLink.notification.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDto {

    private String notiNo;

    private String message;

    private String payNo; // 외래키
}
