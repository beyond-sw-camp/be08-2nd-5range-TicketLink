package com.beyond.ticketLink.user.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserRole {
    private final Long id;
    private final String name;
}
