package com.beyond.ticketLink.board.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 게시글 작성 시 필요한 데이터를 전달하는 DTO 객체
 * tb_board 테이블의 모든 컬럼을 갖는 것이 아닌 게시글 생성에
 * 필요한 데이터만 멤버 변수로 포함된다.
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateDto {
    private String boardNo;
    private String title;
    private String content;
    private String rating;
    private LocalDate insDate;
    private LocalDate uptDate;
    private String userNo;
    private String eventNo;
    private int bCategoryNo;

}
