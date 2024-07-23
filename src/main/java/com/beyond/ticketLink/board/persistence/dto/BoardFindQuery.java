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

    public BoardFindQuery(String boardNo) {
        this.boardNo = boardNo;
    }

    public BoardFindQuery(int bCategoryNo) {
        this.bCategoryNo = bCategoryNo;
    }
}
