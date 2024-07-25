package com.beyond.ticketLink.user.persistence.repository;


import com.beyond.ticketLink.user.persistence.mariadb.dto.JwtCreateDto;
import com.beyond.ticketLink.user.persistence.mariadb.repository.JwtRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JwtRepositoryImplTest {

    @Autowired
    private JwtRepository repository;

    @Test
    @Transactional
    void save_shouldSaveSuccessfully() {
        // given
        String dummyUserNo = "DUMMYA";

        JwtCreateDto jwtCreateDto = new JwtCreateDto(
                "testRefreshToken",
                dummyUserNo
        );

        // when

        // then
        assertThatCode(() -> repository.save(jwtCreateDto))
                .doesNotThrowAnyException();
    }

    @Test
    void save_shouldSaveThrowErrorWithSQLError() {
        // given
        String notExistDummyNo = "qweqrr2r43";

        JwtCreateDto jwtCreateDto = new JwtCreateDto(
                "testRefreshToken",
                notExistDummyNo
        );
        // when

        // then
        assertThatThrownBy(() -> repository.save(jwtCreateDto))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Transactional
    void delete_shouldDeleteSuccessfully() {
        // given
        String dummyUserNo = "DUMMYA";

        JwtCreateDto jwtCreateDto = new JwtCreateDto(
                "testRefreshToken",
                dummyUserNo
        );


        repository.save(jwtCreateDto);

        // when & Then
        assertThatCode(() -> repository.delete(dummyUserNo))
                .doesNotThrowAnyException();

        assertThat(repository.findByUserNo(dummyUserNo).isEmpty())
                .isTrue();
    }

}