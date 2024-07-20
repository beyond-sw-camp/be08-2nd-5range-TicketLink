package com.beyond.ticketLink.reply.application.service;

import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

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
        private final int cnt;
        private final String content;
        private final Date insDate;
        private final Date uptDate;
        private final String boardNo;
        private final String userNo;

        static FindReplyResult findByCreateDto(ReplyCreateDto dto) {
            return FindReplyResult.builder()
                    .replyNo(dto.getReplyNo())
                    .cnt(dto.getCnt())
                    .content(dto.getContent())
                    .insDate(dto.getInsDate())
                    .build();
        }

        static FindReplyResult findByUpdateDto(ReplyUpdateDto dto) {
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
        private final String userNo;
        private final String content;
    }

    @Getter
    @Builder
    @ToString
    class ReplyDeleteCommand {
        private final String replyNo;
        private final String userNo;
    }
}
