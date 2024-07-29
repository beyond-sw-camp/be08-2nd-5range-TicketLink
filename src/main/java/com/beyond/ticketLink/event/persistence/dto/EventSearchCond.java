package com.beyond.ticketLink.event.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventSearchCond {
    private String name;    // 행사이름
    private Integer eCategoryNo;
}
