package com.beyond.ticketLink.event.persistence.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {
    private String name;    // 행사이름
    private LocalDate startDate; // 시작일자
    private LocalDate endDate;   // 종료일자
    private String eTime;   // 시각
    private String location;    // 장소
    private String info;    // 행사정보
    private String saleInfo;    // 판매정보
    private String seatInfo;    // 좌석정보
    private Integer eCategoryNo;
}
