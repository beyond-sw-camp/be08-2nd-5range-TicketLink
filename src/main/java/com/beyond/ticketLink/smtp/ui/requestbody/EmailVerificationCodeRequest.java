package com.beyond.ticketLink.smtp.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;

public record EmailVerificationCodeRequest(
        @NotEmpty String verificationCode
) {
}
