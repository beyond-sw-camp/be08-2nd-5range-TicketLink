package com.beyond.ticketLink.user.persistence.repository;


import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class JwtRepositoryImplTest {

    @Autowired
    private JwtRepository repository;

    @Test
    @Transactional
    void save_shouldSaveSuccessfully() {
        // given
        String dummyUserNo = "DUMMYA";

        JwtCreateDto jwtCreateDto = JwtCreateDto.builder()
                .accessToken("testAccessToken")
                .refreshToken("testRefreshToken")
                .userNo(dummyUserNo)
                .build();
        // when

        // then
        assertThatCode(() -> repository.save(jwtCreateDto))
                .doesNotThrowAnyException();
    }

    @Test
    void save_shouldSaveThrowErrorWithSQLError() {
        // given
        String notExistDummyNo = "qweqrr2r43";

        JwtCreateDto jwtCreateDto = JwtCreateDto.builder()
                .accessToken("testAccessToken")
                .refreshToken("testRefreshToken")
                .userNo(notExistDummyNo)
                .build();
        // when

        // then
        assertThatThrownBy(() -> repository.save(jwtCreateDto))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}