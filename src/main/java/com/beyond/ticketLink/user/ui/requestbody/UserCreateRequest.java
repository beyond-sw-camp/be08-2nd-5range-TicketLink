package com.beyond.ticketLink.user.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record UserCreateRequest(
        @NotEmpty
        @Length(min = 6, max = 20)
        String id,

        @NotEmpty
        @Length(min = 8, max = 12)
        String pw,

        @NotEmpty
        String name,

        @NotEmpty
        String email
) {
}
