package com.beyond.ticketLink.event.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    private String eventNo; // 행사번호, pk
    private String name;    // 행사이름
    private LocalDate startDate; // 시작일자
    private LocalDate endDate;   // 종료일자
    private String eTime;   // 시각
    private String location;    // 장소
    private String info;    // 행사정보
    private String saleInfo;    // 판매정보
    private String seatInfo;    // 좌석정보
    private String timeInfo;    // 시간정보
    private Integer eCategoryNo;    // 행사 카테고리
    private List<DailyEvent> dayEvents;
}
