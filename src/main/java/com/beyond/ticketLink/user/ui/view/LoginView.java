package com.beyond.ticketLink.user.ui.view;

import com.beyond.ticketLink.user.application.service.UserService.FindJwtResult;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginView(
        String accessToken,
        String refreshToken
) {

    public LoginView(FindJwtResult jwtToken) {
        this(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
