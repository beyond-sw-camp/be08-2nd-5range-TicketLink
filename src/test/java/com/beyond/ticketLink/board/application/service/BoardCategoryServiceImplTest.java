package com.beyond.ticketLink.board.application.service;


import com.beyond.ticketLink.board.exception.BoardCategoryMessageType;
import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.beyond.ticketLink.board.application.service.BoardCategoryService.BoardCategoryFindQuery;
import static com.beyond.ticketLink.board.application.service.BoardCategoryService.FindCategoryResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BoardCategoryServiceImplTest {

    @Autowired
    private BoardCategoryService boardCategoryService;

    @Test
    void getCategoryByNo_ShouldReturnCorrectCategoryDetails() {
        // given
        BoardCategoryFindQuery queryWithNo = new BoardCategoryFindQuery(1);
        // when
        FindCategoryResult boardCategory = boardCategoryService.getCategoryByNo(queryWithNo);
        // then
        assertThat(boardCategory.getBCategoryNo()).isEqualTo(queryWithNo.getBCategoryNo());
        assertThat(boardCategory.getName()).isEqualTo("관람후기");
    }

    @Test
    void getCategoryByNo_ShouldThrowExceptionForNonExistentNo() {
        // given
        int notExistNo = Integer.MIN_VALUE;
        BoardCategoryFindQuery queryWithNo = new BoardCategoryFindQuery(notExistNo);
        // when & then
        assertThatThrownBy(() -> boardCategoryService.getCategoryByNo(queryWithNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(BoardCategoryMessageType.BOARD_CATEGORY_NOT_FOUND.getMessage());
    }

    @Test
    void getAllCategory_ShouldReturnCorrectNumberOfCategories() {
        // given
        int currentCategorySize = 3;
        // when
        List<FindCategoryResult> allCategory = boardCategoryService.getAllCategory();
        // then
        assertThat(allCategory.size()).isEqualTo(currentCategorySize);
    }
}