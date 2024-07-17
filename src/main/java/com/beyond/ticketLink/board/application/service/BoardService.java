package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public interface BoardService {

    // 게시판 작성
    Board createBoard(BoardCreateCommand command);


    // 게시판 작성에 필요한 데이터를 담는 VO 객체
    @Getter
    @Builder
    @ToString
    class BoardCreateCommand {
        private final String content;
    }

}
