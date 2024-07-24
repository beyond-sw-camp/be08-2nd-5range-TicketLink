package com.beyond.ticketLink.reply.application.service;

import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.dummy.DummyBoard;
import com.beyond.ticketLink.dummy.DummyReply;
import com.beyond.ticketLink.dummy.DummyUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.beyond.ticketLink.reply.application.service.ReplyService.*;
import static org.assertj.core.api.Assertions.*;

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
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    @Transactional
    void createReply_shouldCreateReplySuccessfully() {
        // given
        ReplyCreateCommand command = ReplyCreateCommand.builder()
                .content("create command 1")
                .boardNo(DummyBoard.DM_B_01.name())
                .userNo(DummyUser.DM_U_03.name())
                .build();
        // when
        FindReplyResult result = replyService.createReply(command);
        // then
        log.info(result.toString());
        assertThat(result.getCnt()).isEqualTo(2);
        assertThat(result.getContent()).isEqualTo(command.getContent());
        assertThat(result.getInsDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    @Transactional
    void createReply_shouldCreateReplyThrowError() {
        // given
        ReplyCreateCommand command = ReplyCreateCommand.builder()
                .content("create command 1")
                .boardNo(DummyBoard.DM_B_01.name())
                .userNo("cvvvcvcv")
                .build();
        // when & then
//        FindReplyResult result = replyService.createReply(command);
        // then

    }

    @Test
    @Transactional
    void updateReply_shouldUpdateReplySuccessfully() {
        // given
        String dummyReplyNo = DummyReply.DM_R_02.name();
        String dummyUserNo = DummyUser.DM_U_01.name();

        ReplyUpdateCommand replyUpdateCommand = ReplyUpdateCommand.builder()
                .replyNo(dummyReplyNo)
                .content("updated dummy Test")
                .userNo(dummyUserNo)
                .build();
        // when & then
        assertThatCode(() -> replyService.modifyReply(replyUpdateCommand))
                .doesNotThrowAnyException();

        FindReplyResult result = replyService.modifyReply(replyUpdateCommand);

        assertThat(result.getReplyNo()).isEqualTo(replyUpdateCommand.getReplyNo());
        assertThat(result.getContent()).isEqualTo(replyUpdateCommand.getContent());
        assertThat(result.getUptDate()).isNotNull();
    }

    @Test
    @Transactional
    void updateReply_shouldUpdateReplyThrowExceptionWithNotFound() {
        // given
        String dummyNotExistReplyNo = DummyReply.DM_NOT_EXIST.name();
        String dummyUserNo = DummyUser.DM_U_01.name();

        ReplyUpdateCommand replyUpdateCommandWithNotExistReplyNo = ReplyUpdateCommand.builder()
                .replyNo(dummyNotExistReplyNo)
                .content("updated dummy Test")
                .userNo(dummyUserNo)
                .build();
        // when & then
        assertThatThrownBy(() -> replyService.modifyReply(replyUpdateCommandWithNotExistReplyNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.NOT_FOUND.getMessage());
    }

    @Test
    @Transactional
    void updateReply_shouldUpdateReplyThrowExceptionWithBadRequest() {
        // given
        String dummyNotExistUserNo = DummyUser.DM_NOT_EXIST.name();
        String notMatchDummyUserNo = DummyUser.DM_U_03.name();
        String dummyReplyNo = DummyReply.DM_R_02.name();

        ReplyUpdateCommand replyUpdateCommandWithNotExistUserNo = ReplyUpdateCommand.builder()
                .replyNo(dummyReplyNo)
                .content("updated dummy Test")
                .userNo(dummyNotExistUserNo)
                .build();

        ReplyUpdateCommand replyUpdateCommandWithNotMatchUserNo = ReplyUpdateCommand.builder()
                .replyNo(dummyReplyNo)
                .content("updated dummy Test")
                .userNo(notMatchDummyUserNo)
                .build();

        // when & then
        assertThatThrownBy(() -> replyService.modifyReply(replyUpdateCommandWithNotExistUserNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.BAD_REQUEST.getMessage());

        assertThatThrownBy(() -> replyService.modifyReply(replyUpdateCommandWithNotMatchUserNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.BAD_REQUEST.getMessage());
    }

    @Test
    @Transactional
    void deleteReply_shouldDeleteReplySuccessfully() {
        // given
        ReplyDeleteCommand replyDeleteCommand = ReplyDeleteCommand.builder()
                .replyNo(DummyReply.DM_R_03.name())
                .userNo(DummyUser.DM_U_02.name())
                .build();
        // when & then
        assertThatCode(() -> replyService.deleteReply(replyDeleteCommand))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    void deleteReply_shouldDeleteReplyThrowErrorWithNotFound() {
        // given
        ReplyDeleteCommand replyDeleteCommand = ReplyDeleteCommand.builder()
                .replyNo(DummyReply.DM_NOT_EXIST.name())
                .userNo(DummyUser.DM_U_02.name())
                .build();
        // when & then
        assertThatThrownBy(() -> replyService.deleteReply(replyDeleteCommand))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.NOT_FOUND.getMessage());
    }

    @Test
    @Transactional
    void deleteReply_shouldDeleteReplyThrowErrorWithBadRequest() {
        // given
        ReplyDeleteCommand replyDeleteCommandWithNotExistUserNo = ReplyDeleteCommand.builder()
                .replyNo(DummyReply.DM_R_03.name())
                .userNo(DummyUser.DM_NOT_EXIST.name())
                .build();

        ReplyDeleteCommand replyDeleteCommandWithNotMatchUserNo = ReplyDeleteCommand.builder()
                .replyNo(DummyReply.DM_R_03.name())
                .userNo(DummyUser.DM_U_04.name())
                .build();
        // when & then
        assertThatThrownBy(() -> replyService.deleteReply(replyDeleteCommandWithNotExistUserNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.BAD_REQUEST.getMessage());

        assertThatThrownBy(() -> replyService.deleteReply(replyDeleteCommandWithNotMatchUserNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(CommonMessageType.BAD_REQUEST.getMessage());
    }
}