package com.beyond.ticketLink.user.ui.requestbody;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty
        @Min(value = 6)
        @Max(value = 20)
        String id,

        @NotEmpty
        @Min(value = 8)
        @Max(value = 12)
        String pw,

        @NotEmpty
        String name,

        @NotEmpty
        String email
) {
}
