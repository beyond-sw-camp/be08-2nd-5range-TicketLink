package com.beyond.ticketLink.reply.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;

public record ReplyCreateRequest(@NotEmpty String content) {
}
