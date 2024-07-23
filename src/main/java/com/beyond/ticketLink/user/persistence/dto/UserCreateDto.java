package com.beyond.ticketLink.user.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String id;
    private String pw;
    private String name;
    private String email;
    private char useYn;
    private Integer roleNo;
}
