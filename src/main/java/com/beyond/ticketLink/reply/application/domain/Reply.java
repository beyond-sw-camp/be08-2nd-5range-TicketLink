package com.beyond.ticketLink.reply.application.domain;

import lombok.*;

import java.sql.Date;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private String replyNo;
    private int cnt;
    private String content;
    private Date insDate;
    private Date uptDate;
    private String boardNo;
    private String userNo;
}
