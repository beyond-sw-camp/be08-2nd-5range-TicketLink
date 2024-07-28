package com.beyond.ticketLink.reply.persistence.mapper;

import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ReplyMapper {
    void insertReply(ReplyCreateDto reply);

    void updateReply(ReplyUpdateDto reply);

    void deleteReply(String replyNo);

    Optional<Reply> selectReplyByReplyNo(String replyNo);

    int selectNextReplyCnt(String boardNo);
}
