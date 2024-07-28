package com.beyond.ticketLink.event.persistence.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventSearchCond {
    private String name;    // 행사이름
    private Integer eCategoryNo;
}
