package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.BoardCategory;
import lombok.*;

import java.util.List;

public interface BoardCategoryService {

    FindCategoryResult getCategoryByNo(BoardCategoryFindQuery query);

    List<FindCategoryResult> getAllCategory();

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
    class FindCategoryResult {
        private int bCategoryNo;
        private String name;

        static FindCategoryResult findByBoardCategory(BoardCategory boardCategory) {
            return FindCategoryResult.builder()
                    .bCategoryNo(boardCategory.getBCategoryNo())
                    .name(boardCategory.getName())
                    .build();
        }
    }
}
