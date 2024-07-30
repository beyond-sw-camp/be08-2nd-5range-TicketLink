package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.exception.BoardMessageType;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.exception.EventMessageType;
import com.beyond.ticketLink.event.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final AutoNoRepository autoNoRepository;

    private final BoardRepository boardRepository;

    private final EventRepository eventRepository;

    private static final LocalDate NOW = LocalDate.now();


    @Override
    @Transactional
    public void createBoard(BoardCreateRequest request, String userNo) {

        Event event = validateEvent(request);

        String boardNo = autoNoRepository.getData("tb_board");

        boardRepository.save(
                new BoardCreateDto(
                        boardNo,
                        request.title(),
                        request.content(),
                        request.rating(),
                        NOW,
                        NOW,
                        userNo,
                        event.getEventNo(),
                        request.bCategoryNo()
                )
        );

    }


    @Override
    public List<FindBoardResult> getAllBoard(BoardFindQuery query) {

        int limit = query.getRow();
        int offset = (query.getPage() - 1) * limit;

        RowBounds rowBounds = new RowBounds(offset, limit);

        List<Board> boards = boardRepository.selectBoardAll(query, rowBounds);

        return boards.stream()
                .map(FindBoardResult::findByBoard)
                .toList();
    }

    @Override
    public FindBoardResult getBoardByNo(BoardFindQuery query) {

        Board board = retrieveBoard(query.getBoardNo());

        return FindBoardResult.findByBoard(board);
    }

    @Override
    public FindBoardResult modifyBoard(BoardUpdateCommand command) {

        Board board = retrieveBoard(command.getBoardNo());

        if (hasNoOperationAuthority(command.getUserNo(), board)) {
            throw new TicketLinkException(BoardMessageType.BOARD_OPERATION_UNAUTHORIZED);
        }

        BoardUpdateDto boardUpdateDto = new BoardUpdateDto(
                board.getBoardNo(),
                command.getContent(),
                command.getTitle(),
                command.getRating(),
                NOW
        );

        boardRepository.updateBoard(boardUpdateDto);

        return FindBoardResult.findByBoardUpdateDto(boardUpdateDto);
    }

    @Override
    public void deleteBoard(BoardDeleteCommand command) {

        Board board = retrieveBoard(command.getBoardNo());

        if (hasNoOperationAuthority(command.getUserNo(), board)) {
            throw new TicketLinkException(BoardMessageType.BOARD_OPERATION_UNAUTHORIZED);
        }

        boardRepository.deleteBoard(command.getBoardNo());

    }

    private Event validateEvent(BoardCreateRequest request) {
        return eventRepository.getData(request.eventNo(), null)
                .orElseThrow(() -> new TicketLinkException(EventMessageType.EVENT_NOT_FOUND));

    }

    private Board retrieveBoard(String boardNo) {
        return boardRepository.selectBoardByBoardNo(boardNo)
                        .orElseThrow(() -> new TicketLinkException(BoardMessageType.BOARD_NOT_FOUND));
    }

    private boolean hasNoOperationAuthority(String userNo, Board board) {
        return !board.getUser().getUserNo().equals(userNo);
    }
}
