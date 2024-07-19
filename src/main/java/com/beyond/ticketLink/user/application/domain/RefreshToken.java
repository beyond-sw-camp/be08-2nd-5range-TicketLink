package com.beyond.ticketLink.user.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private String tokenNo;
    private String refreshToken;
}