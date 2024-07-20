package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.domain.BoardCategory;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.repository.BoardRepository;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    private static final LocalDate now = LocalDate.now();

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
}
