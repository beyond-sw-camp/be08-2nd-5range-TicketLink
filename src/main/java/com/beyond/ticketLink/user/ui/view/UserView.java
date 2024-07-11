package com.beyond.ticketLink.user.ui.view;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserView(
        String id,
        String name,
        String email,
        char useYn,
        String role
) {
    public UserView(TicketLinkUserDetails user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getUseYn(), user.getRole());
    }
}
