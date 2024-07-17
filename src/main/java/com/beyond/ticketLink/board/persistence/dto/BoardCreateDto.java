package com.beyond.ticketLink.board.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 게시글 작성 시 필요한 데이터를 전달하는 DTO 객체
 * tb_board 테이블의 모든 컬럼을 갖는 것이 아닌 게시글 생성에
 * 필요한 데이터만 멤버 변수로 포함된다.
 */
@Getter
@Builder
@ToString
public class BoardCreateDto {
    private final String boardNo;
    private final String content;
    // 아래에 더 추가
}
