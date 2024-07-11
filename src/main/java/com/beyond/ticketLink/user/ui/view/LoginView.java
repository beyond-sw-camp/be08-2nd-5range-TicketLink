package com.beyond.ticketLink.user.ui.view;

import com.beyond.ticketLink.user.application.domain.JwtToken;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginView(
        String accessToken,
        String refreshToken
) {

    public LoginView(JwtToken jwtToken) {
        this(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
