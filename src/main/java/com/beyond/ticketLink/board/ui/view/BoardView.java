package com.beyond.ticketLink.board.ui.view;

import com.beyond.ticketLink.board.application.domain.Board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 클라이언트에게 전달할 데이터를 담는 VO 객체.
 * 이 클래스는 board 도메인과 동일한 멤버 변수를 가진다.
 * API 요청에 따라 적절한 응답 데이터를 포함하여 생성한다.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardView {
    private String title;
    private String content;
    // Board

    public BoardView(Board board) {

    }
}