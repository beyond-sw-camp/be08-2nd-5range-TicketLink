package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.exception.BoardMessageType;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.dummy.DummyBoard;
import com.beyond.ticketLink.dummy.DummyEvent;
import com.beyond.ticketLink.dummy.DummyUser;
import com.beyond.ticketLink.event.exception.EventMessageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.beyond.ticketLink.board.application.service.BoardService.*;
import static org.assertj.core.api.Assertions.*;


@Sql(
        scripts = {
                "/test_sql/user/init.sql",
                "/test_sql/event/init.sql",
                "/test_sql/board/init.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = {
                "/test_sql/user/delete.sql",
                "/test_sql/board/delete.sql",
                "/test_sql/event/delete.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("[createBoard] success case")
    @Transactional
    void createBoard_shouldCreateBoardSuccessfully() {
        // given
        BoardCreateRequest createRequest = new BoardCreateRequest("testBoard", "testBoardContent", 4.5f, DummyEvent.DM_E_02.name(), 1);
        String userNo = DummyUser.DM_U_03.name();

        // when & then
        assertThatCode(() -> boardService.createBoard(createRequest, userNo))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[createBoard] error case #3")
    void createBoard_shouldThrowErrorWithEventNotFound() {
        // given
        BoardCreateRequest createRequest = new BoardCreateRequest("testBoard", "testBoardContent", 4.5f, "vcx,zm", 1);
        String userNo = DummyUser.DM_U_03.name();

        // when & then
        assertThatThrownBy(() -> boardService.createBoard(createRequest, userNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(EventMessageType.EVENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("[modifyBoard] success case")
    @Transactional
    void modifyBoard_shouldModifyBoardSuccessfully() {
        // given
        BoardUpdateCommand updateCommand = BoardUpdateCommand.builder()
                .title("modify test title")
                .content("modify test content")
                .rating(3.5f)
                .boardNo(DummyBoard.DM_B_02.name())
                .userNo(DummyUser.DM_U_04.name())
                .build();
        // when & then
        assertThatCode(() -> boardService.modifyBoard(updateCommand))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[modifyBoard] error case #3 - hasNoOperationAuthority")
    void modifyBoard_shouldModifyBoardThrowErrorWithBadRequest() {
        // given
        String invalidUserNo = DummyUser.DM_U_02.name();
        BoardUpdateCommand updateCommand = BoardUpdateCommand.builder()
                .title("modify test title")
                .content("modify test content")
                .rating(3.5f)
                .boardNo(DummyBoard.DM_B_02.name())
                .userNo(invalidUserNo)
                .build();
        // when & then
        assertThatThrownBy(() -> boardService.modifyBoard(updateCommand))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(BoardMessageType.BOARD_OPERATION_UNAUTHORIZED.getMessage());
    }

    @Test
    @DisplayName("[modifyBoard] error case #4 - board not found")
    void modifyBoard_shouldModifyBoardThrowErrorWithBoardNotFound() {
        // given
        String invalidBoardNo = "ldskflafkds";
        BoardUpdateCommand updateCommand = BoardUpdateCommand.builder()
                .title("modify test title")
                .content("modify test content")
                .rating(3.5f)
                .boardNo(invalidBoardNo)
                .userNo(DummyUser.DM_U_02.name())
                .build();
        // when & then
        assertThatThrownBy(() -> boardService.modifyBoard(updateCommand))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(BoardMessageType.BOARD_NOT_FOUND.getMessage());
    }
}