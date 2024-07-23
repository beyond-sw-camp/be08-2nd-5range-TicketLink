package com.beyond.ticketLink.user.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtCreateDto {
    private final String refreshToken;
    private final String userNo;
}
