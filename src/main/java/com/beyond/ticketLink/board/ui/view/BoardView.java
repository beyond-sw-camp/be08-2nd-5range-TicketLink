package com.beyond.ticketLink.board.ui.view;

import com.beyond.ticketLink.board.application.service.BoardService.FindBoardResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

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
    // 외래키들
        String userNo,
        String eventNo
//        int bCategoryNo

){
    public BoardView(FindBoardResult board) {
        this(board.getBoardNo(), board.getTitle(), board.getContent(), board.getRating(),
                board.getInsDate(), board.getUptDate(), board.getUserNo(), board.getEventNo());
    }
}