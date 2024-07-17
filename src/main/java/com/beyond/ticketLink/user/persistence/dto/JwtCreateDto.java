package com.beyond.ticketLink.user.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class JwtCreateDto {
    private final String refreshToken;
    private final String userNo;
}
