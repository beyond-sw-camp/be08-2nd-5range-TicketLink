package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.BoardCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardCategoryImplTest {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Test
    void findByNo_ShouldReturnBoardCategoryWithMatchingNo() {
        // given
        int categoryNo = 1;
        String categoryName = "관람후기";
        // when
        Optional<BoardCategory> boardCategory = boardCategoryRepository.findByNo(categoryNo);
        // then
        assertThat(boardCategory.isPresent()).isTrue();
        assertThat(boardCategory.get().getBCategoryNo()).isEqualTo(categoryNo);
        assertThat(boardCategory.get().getName()).isEqualTo(categoryName);
    }

    @Test
    void findAll_ShouldReturnCorrectNumberOfCategories() {
        // given
        int currentCategorySize = 3;
        // when
        List<BoardCategory> boardCategories = boardCategoryRepository.findAll();
        // then
        assertThat(boardCategories.size()).isEqualTo(currentCategorySize);
    }

}