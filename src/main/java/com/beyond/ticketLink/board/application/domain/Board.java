package com.beyond.ticketLink.board.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;


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
    private Date insDate;
    private Date uptDate;
    // 외래키들
    private String userNo;
    private String eventNo;
    private BoardCategory category;

}