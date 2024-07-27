package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.exception.BoardMessageType;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.event.application.service.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    // 비즈니스 로직 작성 중 예외 처리할 사항이 생길 경우
    // com.beyond.ticketLink.common.exception 에 위치한
    // MessageType 객체에 에러 추가해서 사용하기

    private final BoardRepository boardRepository;
    private final EventService eventService;

    private static final LocalDate now = LocalDate.now();

    public static final Date DB_GENERATED_DATE = null;

    @Override
    public void createBoard(BoardCreateRequest request, String userNo) {

        // 없는 eventNo로 추가하려고 할 때, NOT_FOUND
        eventService.getData(request.eventNo()).orElseThrow(
                () -> new TicketLinkException(CommonMessageType.NOT_FOUND)
        );



        // insDate, uptDate 는 현재 날짜로 service에서 설정
        // userNo 는  Controller session에서 가져오기
        boardRepository.save(
                new BoardCreateDto(
                        null,
                        request.title(),
                        request.content(),
                        request.rating(),
                        now,
                        now,
                        userNo,
                        request.eventNo(),
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
                DB_GENERATED_DATE
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

    private Board retrieveBoard(String boardNo) {
        return boardRepository.selectBoardByBoardNo(boardNo)
                        .orElseThrow(() -> new TicketLinkException(BoardMessageType.BOARD_NOT_FOUND));
    }

    private boolean hasNoOperationAuthority(String userNo, Board board) {
        return !board.getUser().getUserNo().equals(userNo);
    }
}
