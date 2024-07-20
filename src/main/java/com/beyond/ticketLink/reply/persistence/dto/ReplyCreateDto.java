package com.beyond.ticketLink.reply.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreateDto {
    private String replyNo;
    private String content;
    private Integer cnt;
    private Date insDate;
    private String userNo;
    private String boardNo;

    public static class SelectKeyResult {
        private String replyNo;
        private Integer cnt;
        private Date insDate;
    }
}
