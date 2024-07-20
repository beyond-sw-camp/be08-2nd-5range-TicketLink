package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;



    @Test
    @DisplayName("qhe 테스트")
    @Transactional
    void createBoard() {
        //given
//        .title(request.title())
//                .content(request.content())
//                .rating(request.rating())
//                .insDate(now)
//                .uptDate(now)
//                .userNo(userNo)
//                .eventNo(request.eventNo())
//                .bCategoryNo(Integer.parseInt(request.bCategoryNo()))
        String dummyTitle = "hello world";
        String dummyContent = "content testing...";
        String dummyRating = "4";
        String dummyEventNo = "EV00000001";
        int dummyBCategoryNo = 1;

        BoardCreateRequest createRequest = new BoardCreateRequest(dummyTitle,dummyContent,dummyRating,dummyEventNo,dummyBCategoryNo);

        String dummyUserNo = "DUMMYA";

        //when
        boardService.createBoard(createRequest, dummyUserNo);


        //then
        assertThat(boardService).isNotNull();


    }
}