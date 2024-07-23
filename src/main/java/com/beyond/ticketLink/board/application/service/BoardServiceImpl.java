package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    private static final LocalDate now = LocalDate.now();

    public static final Date DB_GENERATED_DATE = null;

    @Override
    public void createBoard(BoardCreateRequest request, String userNo) {

        // 비즈니스 로직 작성 중 예외 처리할 사항이 생길 경우
        // com.beyond.ticketLink.common.exception 에 위치한
        // MessageType 객체에 에러 추가해서 사용하기


        // insDate, uptDate 는 현재 날짜로 service에서 설정
        // userNo 는  Controller session에서 가져오기
        boardRepository.save(
                BoardCreateDto.builder()
                        .title(request.title())
                        .content(request.content())
                        .rating(request.rating())
                        .insDate(now)
                        .uptDate(now)
                        .userNo(userNo)
                        .eventNo(request.eventNo())
                        .bCategoryNo(request.bCategoryNo())
                        .build()
        );
    }

    @Override
    public List<FindBoardResult> getAllBoard(BoardFindQuery query) {

        List<Board> boards = boardRepository.selectBoardAll(query);

        return boards.stream()
                .map(FindBoardResult::findByBoard)
                .toList();
    }

    @Override
    public FindBoardResult getBoardByNo(BoardFindQuery query) {

        Board board = boardRepository.selectBoardByBoardNo(query.getBoardNo())
                .orElseThrow(() -> new TicketLinkException(MessageType.NOT_FOUND));

        return FindBoardResult.findByBoard(board);
    }

    @Override
    public FindBoardResult modifyBoard(BoardUpdateCommand command) {

        Board board = retrieveBoard(command.getBoardNo());

        if (hasNoOperationAuthority(command.getUserNo(), board)) {
            throw new TicketLinkException(MessageType.BAD_REQUEST);
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
            throw new TicketLinkException(MessageType.BAD_REQUEST);
        }

        boardRepository.deleteBoard(command.getBoardNo());

    }

    private Board retrieveBoard(String boardNo) {
        return boardRepository.selectBoardByBoardNo(boardNo)
                        .orElseThrow(() -> new TicketLinkException(MessageType.NOT_FOUND));
    }

    private boolean hasNoOperationAuthority(String userNo, Board board) {
        return !board.getUserNo().equals(userNo);
    }
}