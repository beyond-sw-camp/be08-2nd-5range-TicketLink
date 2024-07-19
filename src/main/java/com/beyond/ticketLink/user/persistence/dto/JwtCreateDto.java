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
    private String accessToken;
    private String refreshToken;
    private String userNo;
}
