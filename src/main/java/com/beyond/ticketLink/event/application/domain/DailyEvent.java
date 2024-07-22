package com.beyond.ticketLink.event.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyEvent {
    private String dayEventNo;  // 일자별 행사번호, pk
    private LocalDate eventDate; // 행사일자
    private Integer day7;  // 요일
    private Integer cnt;    // 순번
    private String deTime; // 회차별 시간 정보
    private String castInfo;    // 회차별 캐스팅 정보
    private String eventNo;     // 행사번호, fk
}
