package com.beyond.ticketLink.board.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardFindQuery {
    private String boardNo;
    private Integer bCategoryNo;

    private Integer page;
    private final Integer row = 10;

    public BoardFindQuery(String boardNo) {
        this.boardNo = boardNo;
    }

    public BoardFindQuery(Integer bCategoryNo, Integer page) {
        this.bCategoryNo = bCategoryNo;
        this.page = page;
    }
}
