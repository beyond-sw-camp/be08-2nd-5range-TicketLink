package com.beyond.ticketLink.board.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BoardUpdateRequest(
        @NotEmpty
        String title,
        @NotEmpty
        String content,
        @NotNull
        Float rating
) {
}
