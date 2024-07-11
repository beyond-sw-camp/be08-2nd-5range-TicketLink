package com.beyond.ticketLink.user.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class JwtToken {
    private final String accessToken;
    private final String refreshToken;
}
