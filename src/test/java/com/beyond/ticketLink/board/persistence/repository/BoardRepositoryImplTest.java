package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryImplTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("djkdfjkdf")
    void save() {
        // given
        BoardCreateDto createDto = BoardCreateDto.builder()
                .content("")
                .build();
        // when

        // then
        assertThatCode(() -> boardRepository.save(createDto))
                .doesNotThrowAnyException();
    }
}