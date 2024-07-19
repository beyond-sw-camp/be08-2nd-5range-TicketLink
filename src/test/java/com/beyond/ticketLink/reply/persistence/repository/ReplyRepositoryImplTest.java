package com.beyond.ticketLink.reply.persistence.repository;

import com.beyond.ticketLink.dummy.DummyBoard;
import com.beyond.ticketLink.dummy.DummyReply;
import com.beyond.ticketLink.dummy.DummyUser;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
class ReplyRepositoryImplTest {

    @Autowired
    private ReplyRepository replyRepository;

    public static final String GENERATED_REPLY_NO = null;
    public static final Integer GENERATED_CNT = null;
    public static final Date GENERATED_DATE = null;

    @Test
    @Transactional
    void insertReply_shouldInsertReplySetReplyNoCorrectly() {
        // given
        ReplyCreateDto createDto = new ReplyCreateDto(
                GENERATED_REPLY_NO,
                "test content",
                GENERATED_CNT,
                DummyUser.DM_U_01.name(),
                DummyBoard.DM_B_01.name()
        );
        // when
        replyRepository.insertReply(createDto);
        // then
        assertThat(createDto.getReplyNo()).isNotNull();
    }

    @Test
    @Transactional
    void insertReply_shouldInsertReplyWithCorrectCnt() {
        // given
        ReplyCreateDto secondReply = new ReplyCreateDto(
                GENERATED_REPLY_NO,
                "test",
                GENERATED_CNT,
                DummyUser.DM_U_03.name(),
                DummyBoard.DM_B_01.name()
        );

        ReplyCreateDto thirdReply = new ReplyCreateDto(
                GENERATED_REPLY_NO,
                "test",
                GENERATED_CNT,
                DummyUser.DM_U_04.name(),
                DummyBoard.DM_B_01.name()
        );
        // when
        replyRepository.insertReply(secondReply);
        replyRepository.insertReply(thirdReply);
        // then
        assertThat(secondReply.getCnt()).isNotNull();
        assertThat(secondReply.getCnt()).isEqualTo(2);

        assertThat(thirdReply.getCnt()).isNotNull();
        assertThat(thirdReply.getCnt()).isEqualTo(3);

    }

    @Test
    @Transactional
    void insertReply_shouldInsertReplyThrowErrorWithDataIntegrity() {
        // given
        String userNo = "DUMMYA";
        String boardNo = "BD00000001";
        String notExistUserNo = "bvbvbv";
        String notExistBoardNo = "zxcxczx";

        ReplyCreateDto createDtoWithInvalidBoardNo = new ReplyCreateDto(
                GENERATED_REPLY_NO,
                "test",
                GENERATED_CNT,
                userNo,
                notExistBoardNo
        );
        ReplyCreateDto createDtoWithInvalidUserNo = new ReplyCreateDto(
                GENERATED_REPLY_NO,
                "test",
                GENERATED_CNT,
                notExistUserNo,
                boardNo
        );
        // when & then
        assertThatThrownBy(() -> replyRepository.insertReply(createDtoWithInvalidBoardNo))
                .isInstanceOf(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> replyRepository.insertReply(createDtoWithInvalidUserNo))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Transactional
    void updateReply_shouldUpdateReplySuccessfully() {
        // given
        Optional<Reply> before = replyRepository.selectReplyByReplyNo(DummyReply.DM_R_01.name());
        assertThat(before.isPresent()).isTrue();

        ReplyUpdateDto updateDto = new ReplyUpdateDto(
                before.get().getReplyNo(),
                "update reply",
                GENERATED_DATE
        );

        // when
        replyRepository.updateReply(updateDto);
        Optional<Reply> after = replyRepository.selectReplyByReplyNo(DummyReply.DM_R_01.name());
        // then

        assertThat(after.isPresent()).isTrue();

        assertThat(before.get().getContent()).isNotEqualTo(after.get().getContent());
        assertThat(before.get().getUptDate()).isNotEqualTo(after.get().getUptDate());

        assertThat(after.get().getContent()).isEqualTo(updateDto.getContent());
        assertThat(after.get().getUptDate()).isEqualTo(updateDto.getUptDate());
    }

    @Test
    @Transactional
    void deleteReply_ShouldDeleteReplySuccessfully() {
        // given

        // when
        replyRepository.deleteReply(DummyReply.DM_R_01.name());
        // then
        Optional<Reply> target = replyRepository.selectReplyByReplyNo(DummyReply.DM_R_01.name());
        assertThat(target.isEmpty()).isTrue();
    }

}