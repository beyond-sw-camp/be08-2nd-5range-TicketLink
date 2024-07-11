package com.beyond.ticketLink.user.ui.requestbody;

import jakarta.validation.constraints.NotEmpty;

public record CheckDuplicateIdRequest(@NotEmpty String id) {
}
