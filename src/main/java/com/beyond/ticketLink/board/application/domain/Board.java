package com.beyond.ticketLink.board.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    // 아래에 더 추가
}