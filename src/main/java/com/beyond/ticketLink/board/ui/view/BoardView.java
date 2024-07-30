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


@JsonInclude(JsonInclude.Include.NON_NULL)
public record BoardView (
        String boardNo,
        String title,
        String content,
        Float rating,
        Date insDate,
        Date uptDate,
        Integer boardCategoryNo,
        String boardCategoryName,
        String username,
        List<FindReplyResult> replies

){
    public BoardView(FindBoardResult board) {
        this(
                board.getBoardNo(),
                board.getTitle(),
                board.getContent(),
                board.getRating(),
                board.getInsDate(),
                board.getUptDate(),
                board.getBoardCategoryNo(),
                board.getBoardCategoryName(),
                board.getUsername(),
                board.getReplies()
        );

    }
}