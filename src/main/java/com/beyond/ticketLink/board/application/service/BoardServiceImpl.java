package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    @Override
    public Board createBoard(BoardCreateCommand command) {

        // 비즈니스 로직 작성 중 예외 처리할 사항이 생길 경우
        // com.beyond.ticketLink.common.exception 에 위치한
        // MessageType 객체에 에러 추가해서 사용하기
        return null;
    }
}
