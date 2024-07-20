package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryImplTest {
    private static final LocalDate now = LocalDate.now();



    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @DisplayName("보드 생성 레포 테스트")
    void save() {
        // given
        BoardCreateDto createDto = BoardCreateDto.builder()
                .title("Hello world")
                .content("content test")
                .rating("4")
                .insDate(now)
                .uptDate(now)
                .userNo("DUMMYA")
                .eventNo("EV00000001")
                .bCategoryNo(1)
                .build();
        // when
        boardRepository.save(createDto);


        // then

    }
}