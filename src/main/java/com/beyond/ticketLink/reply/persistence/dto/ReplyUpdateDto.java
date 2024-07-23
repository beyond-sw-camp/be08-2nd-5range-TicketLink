package com.beyond.ticketLink.reply.persistence.dto;

import lombok.*;

import java.sql.Date;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyUpdateDto {
    private String replyNo;
    private String content;
    private Date uptDate;

    public static class SelectKeyResult {
        private Date uptDate;
    }
}
