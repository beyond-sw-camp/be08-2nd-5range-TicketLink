package com.beyond.ticketLink.reply.application.service;

import com.beyond.ticketLink.autono.application.domain.AutoNo;
import com.beyond.ticketLink.autono.persistence.mapper.AutoNoMapper;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.exception.ReplyMessageType;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.persistence.dto.ReplyUpdateDto;
import com.beyond.ticketLink.reply.persistence.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    private final BoardRepository boardRepository;

    private final AutoNoMapper autoNoMapper;

    private static final Date NOW = Date.valueOf(LocalDate.now());



    @Override
    @Transactional
    public FindReplyResult createReply(ReplyCreateCommand command) {

        // 댓글 작성 대상 게시판 검증
        String validBoardNo = validateBoard(command.getBoardNo());

        String replyNo = generateReplyNo();

        int nextReplyCnt = replyRepository.selectNextReplyCnt(command.getBoardNo());

        ReplyCreateDto createDto = new ReplyCreateDto(
                replyNo,
                command.getContent(),
                nextReplyCnt,
                NOW,
                NOW,
                command.getUserNo(),
                validBoardNo
        );

        replyRepository.insertReply(createDto);

        return FindReplyResult.findByCreateDto(createDto);
    }

    @Override
    @Transactional
    public FindReplyResult modifyReply(ReplyUpdateCommand command) {


        validateBoard(command.getBoardNo());

        Reply reply = retrieveReply(command.getReplyNo());

        if (hasNoOperationAuthority(command.getUserNo(), reply)) {
            throw new TicketLinkException(ReplyMessageType.REPLY_OPERATION_UNAUTHORIZED);
        }

        ReplyUpdateDto updateDto = new ReplyUpdateDto(
                reply.getReplyNo(),
                command.getContent(),
                NOW
        );

        replyRepository.updateReply(updateDto);

        return FindReplyResult.findByUpdateDto(updateDto);
    }

    @Override
    @Transactional
    public void deleteReply(ReplyDeleteCommand command) {

        validateBoard(command.getBoardNo());

        Reply validReply = retrieveReply(command.getReplyNo());

        if (hasNoOperationAuthority(command.getUserNo(), validReply)) {
            throw new TicketLinkException(ReplyMessageType.REPLY_OPERATION_UNAUTHORIZED);
        }

        replyRepository.deleteReply(validReply.getReplyNo());
    }


    private String generateReplyNo() {
        final String TABLE_NAME = "tb_reply";

        AutoNo replyNo = autoNoMapper.getData(TABLE_NAME)
                .orElseThrow(() -> new TicketLinkException(ReplyMessageType.GENERATE_REPLY_NO_FAILED));
        return replyNo.getAutoNo();
    }

    private String validateBoard(String boardNo) {
        return boardRepository.selectBoardByBoardNo(boardNo)
                .orElseThrow(() -> new TicketLinkException(ReplyMessageType.REPLY_TARGET_BOARD_NOT_FOUND))
                .getBoardNo();
    }

    private Reply retrieveReply(String replyNo) {
        return replyRepository.selectReplyByReplyNo(replyNo)
                .orElseThrow(() -> new TicketLinkException(ReplyMessageType.REPLY_NOT_FOUND));
    }

    private boolean hasNoOperationAuthority(String userNo, Reply reply) {
        return !userNo.equals(reply.getUser().getUserNo());
    }
}
