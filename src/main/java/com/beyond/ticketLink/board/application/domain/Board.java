package com.beyond.ticketLink.board.application.domain;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


/**
 * tb_board 테이블의 모든 컬럼을 읽어올 때 사용하는 VO 객체
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private String boardNo;
    private String title;
    private String content;
    private Float rating;
    private LocalDate insDate;
    private LocalDate uptDate;

    private TicketLinkUserDetails user;
    private Event event;
    private BoardCategory category;

    private List<Reply> replies;

}