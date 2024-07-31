package com.beyond.ticketLink.event.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class EventStatsDto {
    private String eventNo;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer resCnt;
    private Integer allCnt;
    private Integer eCategoryNo;
}
