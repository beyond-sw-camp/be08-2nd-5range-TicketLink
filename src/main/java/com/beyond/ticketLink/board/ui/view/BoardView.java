package com.beyond.ticketLink.board.ui.view;

import com.beyond.ticketLink.board.application.service.BoardService.FindBoardResult;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.application.service.ReplyService;
import com.beyond.ticketLink.reply.application.service.ReplyService.FindReplyResult;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.application.service.UserService.FindUserResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;

/**
 * 클라이언트에게 전달할 데이터를 담는 VO 객체.
 * 이 클래스는 board 도메인과 동일한 멤버 변수를 가진다.
 * API 요청에 따라 적절한 응답 데이터를 포함하여 생성한다.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BoardView (
        String boardNo,
        String title,
        String content,
        Float rating,
        Date insDate,
        Date uptDate,
        FindUserResult user,
        Event event,
        List<FindReplyResult> replies

){
    public BoardView(FindBoardResult board) {
        this(board.getBoardNo(), board.getTitle(), board.getContent(), board.getRating(),
                board.getInsDate(), board.getUptDate(), board.getUser(), board.getEvent(),
                board.getReplies()
        );
    }
}