package com.beyond.ticketLink.smtp.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;

public record EmailVerificationRequest(
        @NotEmpty String email
) {
}
