package com.beyond.ticketLink.event.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayEventSearchCond {
    private LocalDate sDate;
    private LocalDate eDate;
    private String dayInfo;
    private List<String> days;
    private String timeInfo;
    private List<String> times;
    private String castInfo;
    private List<String> casts;
}