package com.beyond.ticketLink.reply.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreateDto {
    private String replyNo;
    private String content;
    private Integer cnt;
    private String userNo;
    private String boardNo;

    public static class SelectKeyResult {
        private String replyNo;
        private Integer cnt;
    }
}
