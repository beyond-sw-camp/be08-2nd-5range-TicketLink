package com.beyond.ticketLink.reply.application.service;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import com.beyond.ticketLink.reply.persistence.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    public static final String DB_GENERATED_REPLY_ID = null;
    public static final Integer DB_GENERATED_CNT = null;
    public static final Date DB_GENERATED_DATE = null;


    @Override
    @Transactional
    public FindReplyResult createReply(ReplyCreateCommand command) {

        ReplyCreateDto createDto = new ReplyCreateDto(
                DB_GENERATED_REPLY_ID,
                command.getContent(),
                DB_GENERATED_CNT,
                DB_GENERATED_DATE,
                command.getUserNo(),
                command.getBoardNo()
        );

        // TODO boardNo에 대한 유효성 검사

        replyRepository.insertReply(createDto);

        return FindReplyResult.findByCreateDto(createDto);
    }

    @Override
    @Transactional
    public FindReplyResult modifyReply(ReplyUpdateCommand command) {

        String replyNo = command.getReplyNo();
        String content = command.getContent();
        String userNo = command.getUserNo();

        Reply reply = retrieveReply(replyNo);

        if (hasNoOperationAuthority(userNo, reply)) {
            throw new TicketLinkException(MessageType.BAD_REQUEST);
        }

        ReplyUpdateDto updateDto = new ReplyUpdateDto(
                replyNo,
                content,
                DB_GENERATED_DATE
        );

        replyRepository.updateReply(updateDto);

        return FindReplyResult.findByUpdateDto(updateDto);
    }

    @Override
    @Transactional
    public void deleteReply(ReplyDeleteCommand command) {
        String replyNo = command.getReplyNo();
        String userNo = command.getUserNo();

        Reply reply = retrieveReply(replyNo);

        if (hasNoOperationAuthority(userNo, reply)) {
            throw new TicketLinkException(MessageType.BAD_REQUEST);
        }

        replyRepository.deleteReply(replyNo);
    }

    private Reply retrieveReply(String replyNo) {
        return replyRepository.selectReplyByReplyNo(replyNo)
                .orElseThrow(() -> new TicketLinkException(MessageType.NOT_FOUND));

    }

    private boolean hasNoOperationAuthority(String userNo, Reply reply) {
        return !userNo.equals(reply.getUser().getUserNo());
    }
}
