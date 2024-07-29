package com.beyond.ticketLink.user.persistence.mariadb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtCreateDto {
    private String refreshToken;
    private String userNo;
}