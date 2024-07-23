package com.beyond.ticketLink.event.persistence.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayEventDto {
    private String eventNo;
    private LocalDate eventDate;
    private Integer dayOfWeek;
    private String timeInfo;
    private String castInfo;
}
