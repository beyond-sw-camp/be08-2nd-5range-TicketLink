package com.beyond.ticketLink.reply.persistence.repository;

import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;

import java.util.Optional;

public interface ReplyRepository {

    void insertReply(ReplyCreateDto reply);

    void updateReply(ReplyUpdateDto reply);

    void deleteReply(String replyNo);

    Optional<Reply> selectReplyByReplyNo(String replyNo);
}
