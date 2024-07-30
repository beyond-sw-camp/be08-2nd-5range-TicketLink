package com.beyond.ticketLink.reply.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.service.BoardService;
import com.beyond.ticketLink.board.application.service.BoardService.FindBoardResult;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.application.service.UserService.FindUserResult;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

import static com.beyond.ticketLink.board.application.service.BoardService.FindBoardResult.*;
import static com.beyond.ticketLink.user.application.service.UserService.FindUserResult.*;

public interface ReplyService {

    FindReplyResult createReply(ReplyCreateCommand command);

    FindReplyResult modifyReply(ReplyUpdateCommand command);

    void deleteReply(ReplyDeleteCommand command);

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class FindReplyResult {
        private final String replyNo;
        private final Integer cnt;
        private final String content;
        private final Date insDate;
        private final Date uptDate;
        private final String boardNo;
        private final String userNo;

        private final FindBoardResult board;
        private final FindUserResult user;

        public static FindReplyResult findByReply(Reply reply) {
            return FindReplyResult.builder()
                    .replyNo(reply.getReplyNo())
                    .cnt(reply.getCnt())
                    .content(reply.getContent())
                    .insDate(reply.getInsDate())
                    .uptDate(reply.getUptDate())
                    .user(findByUser(reply.getUser()))
                    .build();
        }

        public static FindReplyResult findByCreateDto(ReplyCreateDto dto) {
            return FindReplyResult.builder()
                    .replyNo(dto.getReplyNo())
                    .cnt(dto.getCnt())
                    .content(dto.getContent())
                    .insDate(dto.getInsDate())
                    .build();
        }

        public static FindReplyResult findByUpdateDto(ReplyUpdateDto dto) {
            return FindReplyResult.builder()
                    .replyNo(dto.getReplyNo())
                    .content(dto.getContent())
                    .uptDate(dto.getUptDate())
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    class ReplyCreateCommand {
        private final String content;
        private final String boardNo;
        private final String userNo;
    }

    @Getter
    @Builder
    @ToString
    class ReplyUpdateCommand {
        private final String replyNo;
        private final String content;
        private final String boardNo;
        private final String userNo;
    }

    @Getter
    @Builder
    @ToString
    class ReplyDeleteCommand {
        private final String replyNo;
        private final String boardNo;
        private final String userNo;
    }
}
