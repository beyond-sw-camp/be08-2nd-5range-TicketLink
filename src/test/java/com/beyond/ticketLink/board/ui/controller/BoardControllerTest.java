package com.beyond.ticketLink.board.ui.controller;

import com.beyond.ticketLink.board.application.service.BoardService;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    // HTTP 요청을 시뮬레이션하고 응답을 검증하는 데 사용
    @Autowired
    MockMvc mockMvc;

    // java 객체를 JSON으로 변환하는 데 사용
    @Autowired
    private ObjectMapper objectMapper;

    // 실제 서비스 로직이 실행되지 않도록 BoardService의 모의 객체를 등록
    @MockBean
    private BoardService boardService;


    @Test
    // 인증된 사용자로 테스트를 수행 - 인증된 사용자를 따라한 것
    @WithMockUser(username = "user", roles = {"USER"})
    void addBoard() throws Exception {

        //given
        String title = "Title Test";
        String content = "Content Test";
        String rating = "4.5";
        String eventNo = "EV00000001";
        int bCategoryNo = 1;

        String userNo = "DUMMYA";

        BoardCreateRequest boardCreateRequest = new BoardCreateRequest(title, content, rating, eventNo, bCategoryNo);


        //when
        // boardService.createBoard() 메서드가 호출될 때, 아무 작업도 하지 않도록 설정 -> 컨트롤러의 동작만 검증할 수 있도록
        doNothing().when(boardService).createBoard(boardCreateRequest, userNo);

        // POST 요청을 보내고, BoardCreateRequest 객체를 JSON 형식으로 변환하여 요청 본문으로 설정
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardCreateRequest))
        );

        log.debug(boardCreateRequest.content());


        //then
        // 응답 상태 코드가 201 (Created)임을 검증
        resultActions.andExpect(status().isCreated());
    }
}