package com.beyond.ticketLink.reply.application.domain;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Board board;
    private TicketLinkUserDetails user;
}
