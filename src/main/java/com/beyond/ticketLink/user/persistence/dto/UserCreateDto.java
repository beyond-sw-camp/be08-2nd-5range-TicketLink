package com.beyond.ticketLink.user.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserCreateDto {
    private final String id;
    private final String pw;
    private final String name;
    private final String email;
    private final char useYn;
    private final Long roleNo;
}
