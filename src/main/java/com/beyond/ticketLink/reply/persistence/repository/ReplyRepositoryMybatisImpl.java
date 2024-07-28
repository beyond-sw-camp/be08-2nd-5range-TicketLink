package com.beyond.ticketLink.reply.persistence.repository;

import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import com.beyond.ticketLink.reply.persistence.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReplyRepositoryMybatisImpl implements ReplyRepository {

    private final ReplyMapper replyMapper;

    @Override
    public void insertReply(ReplyCreateDto reply) {
        replyMapper.insertReply(reply);
    }

    @Override
    public Optional<Reply> selectReplyByReplyNo(String replyNo) {
        return replyMapper.selectReplyByReplyNo(replyNo);
    }

    @Override
    public int selectNextReplyCnt(String boardNo) {
        return replyMapper.selectNextReplyCnt(boardNo);
    }

    @Override
    public void updateReply(ReplyUpdateDto reply) {
        replyMapper.updateReply(reply);
    }

    @Override
    public void deleteReply(String replyNo) {
        replyMapper.deleteReply(replyNo);
    }
}
