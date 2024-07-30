package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.BoardCategory;
import lombok.*;

import java.util.List;

public interface BoardCategoryService {

    FindBoardCategoryResult getCategoryByNo(BoardCategoryFindQuery query);

    List<FindBoardCategoryResult> selectAllCategory();

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    class BoardCategoryFindQuery {
        private int bCategoryNo;
        private String name;

        public BoardCategoryFindQuery(int bCategoryNo) {
            this.bCategoryNo = bCategoryNo;
        }

        public BoardCategoryFindQuery(String name) {
            this.name = name;
        }
    }


    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class FindBoardCategoryResult {
        private int bCategoryNo;
        private String name;

        static FindBoardCategoryResult findByBoardCategory(BoardCategory boardCategory) {
            return FindBoardCategoryResult.builder()
                    .bCategoryNo(boardCategory.getBCategoryNo())
                    .name(boardCategory.getName())
                    .build();
        }
    }
}
