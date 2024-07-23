package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.dummy.DummyBoard;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Sql(
        scripts = {
                "/test_sql/user/init.sql",
                "/test_sql/event/init.sql",
                "/test_sql/board/init.sql",
                "/test_sql/reply/init.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = {
                "/test_sql/reply/delete.sql",
                "/test_sql/board/delete.sql",
                "/test_sql/event/delete.sql",
                "/test_sql/user/delete.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
class BoardRepositoryImplTest {
    private static final LocalDate now = LocalDate.now();

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @DisplayName("보드 생성 레포 테스트")
    void save() {
        // given
        BoardCreateDto createDto = BoardCreateDto.builder()
                .title("Hello world")
                .content("content test")
                .rating("4")
                .insDate(now)
                .uptDate(now)
                .userNo("DUMMYA")
                .eventNo("EV00000001")
                .bCategoryNo(1)
                .build();
        // when
        boardRepository.save(createDto);
        // then
    }

    @Test
    void selectBoardByBoardNo_shouldReturnBoardWithCorrectBoardNo() {
        // given
        String dummyBoardNo = DummyBoard.DM_B_04.name();
        // when
        Optional<Board> result = boardRepository.selectBoardByBoardNo(dummyBoardNo);
        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getBoardNo()).isEqualTo(dummyBoardNo);
    }

    @Test
    void selectBoardAll() {
        // given
        BoardFindQuery queryCategoryOne = new BoardFindQuery(1);
        BoardFindQuery queryCategoryTwo = new BoardFindQuery(2);

        // when
        List<Board> boardsCategoryOne = boardRepository.selectBoardAll(queryCategoryOne);
        List<Board> boardsCategoryTwo = boardRepository.selectBoardAll(queryCategoryTwo);
        // then
        log.info("boardsCategoryOne size = {}", boardsCategoryOne.size());
        log.info("boardsCategoryTwo size = {}", boardsCategoryTwo.size());
    }

    @Test
    void updateBoard_shouldUpdateBoardSuccessfully() {
        // given
        String boardNo = "DM_B_01";
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto(boardNo, "수정 테스트 내용", "수정 테스트 제목", 3.2f, null);
        // when

        // then
        assertThatCode(() -> boardRepository.updateBoard(boardUpdateDto))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteBoard_shouldDeleteBoardSuccessfully() {
        // given
        String boardNo = "DM_B_01";
        // when
        boardRepository.deleteBoard(boardNo);
        // then
        assertThat(boardRepository.selectBoardByBoardNo(boardNo).isEmpty()).isTrue();
    }
}