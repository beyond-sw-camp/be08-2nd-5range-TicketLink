package com.beyond.ticketLink.user.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequest(
        @NotEmpty String id,
        @NotEmpty String pw
) {
}
