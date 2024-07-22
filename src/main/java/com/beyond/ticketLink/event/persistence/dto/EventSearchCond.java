package com.beyond.ticketLink.event.persistence.dto;

import lombok.Data;

@Data
public class EventSearchCond {
    private String name;    // 행사이름
    private Integer eCategoryNo;
}
