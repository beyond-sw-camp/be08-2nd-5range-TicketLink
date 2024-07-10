package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.persistence.dto.UserCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class UserRepositoryImplTest {

    private static final char ENABLE_USER = 'Y';
    private static final char DISABLE_USER = 'N';

    private static final long ROLE_USER = 2L;
    private static final long ROLE_ADMIN = 1L;

    private static final String DUMMY_USER_A_ID = "dummyUserA";
    private static final String NOT_EXIST_USER_ID = "notExist";



    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("아이디로 유저 찾기 테스트")
    void findUserById() {
        // given

        // when
        Optional<TicketLinkUserDetails> DUMMY_USER_A = repository.findUserById(DUMMY_USER_A_ID);
        Optional<TicketLinkUserDetails> NOT_EXIST_USER = repository.findUserById(NOT_EXIST_USER_ID);
        // then
        assertThat(DUMMY_USER_A.isPresent()).isTrue();
        assertThat(DUMMY_USER_A.get().getId()).isEqualTo(DUMMY_USER_A_ID);
        assertThat(DUMMY_USER_A.get().getRole()).isEqualTo("관리자");

        assertThat(NOT_EXIST_USER.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("유저 생성 테스트")
    void save() {
        // given
        UserCreateDto USER_CREATE_COMMAND = UserCreateDto.builder()
                .id("userA")
                .pw("1234")
                .email("testUserA@beyond.com")
                .name("테스트유저1")
                .useYn(ENABLE_USER)
                .roleNo(ROLE_USER)
                .build();
        // when
        repository.save(USER_CREATE_COMMAND);
        // then
    }
}