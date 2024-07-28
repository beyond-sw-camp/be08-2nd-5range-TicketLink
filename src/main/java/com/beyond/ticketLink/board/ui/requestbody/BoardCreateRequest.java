package com.beyond.ticketLink.board.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// boardNo, title, content, rating, eventNo, bCategoryNo
// insDate, uptDate, userNo 는 세션과 service에서 해결
public record BoardCreateRequest(
        @NotEmpty
        String title,

        @NotEmpty
        String content,

        @NotNull
        Float rating,

        @NotEmpty
        String eventNo,

        @NotEmpty
        int bCategoryNo
) {
}
