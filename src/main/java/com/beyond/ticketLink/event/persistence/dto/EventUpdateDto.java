package com.beyond.ticketLink.event.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EventUpdateDto {
    private String name;    // 행사이름
    private String eTime;   // 시각
    private String location;    // 장소
    private String info;    // 행사정보
    private String saleInfo;    // 판매정보
    private Integer eCategoryNo;
}
