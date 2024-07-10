package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService service;

    private static final String DUMMY_USER_A_ID = "dummyUserA";
    private static final String NOT_EXIST_USER_ID = "notExistUserId";

    @Test
    @DisplayName("유저 회원가입 테스트")
    @Transactional
    void register() {
        // given
        UserCreateRequest REQUEST_REGISTER =
                new UserCreateRequest("testUserA", "testUserA", "testUserA", "testUserA@beyond.com");

        UserCreateRequest REQUEST_REGISTER_DUPLICATE_ID =
                new UserCreateRequest(DUMMY_USER_A_ID, "testUserA", "testUserA", "testUserA@beyond.com");
        // when
        service.register(REQUEST_REGISTER);
        // then
        assertThatThrownBy(() -> service.register(REQUEST_REGISTER_DUPLICATE_ID))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(MessageType.DUPLICATE_USER_ID.getMessage());
    }

    @Test
    @DisplayName("아이디 중복확인 테스트")
    void checkDuplicateUserId() {
        // given

        // when


        // then
        assertThatThrownBy(() -> service.throwErrorWithDuplicateId(DUMMY_USER_A_ID))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(MessageType.DUPLICATE_USER_ID.getMessage());

        assertThatCode(() -> service.throwErrorWithDuplicateId(NOT_EXIST_USER_ID))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("아이디로 조회 테스트")
    void loadUserByUsername() {
        // given

        // when
        UserDetails DUMMY_USER_A = service.loadUserByUsername(DUMMY_USER_A_ID);
        // then
        assertThat(DUMMY_USER_A.getUsername()).isEqualTo(DUMMY_USER_A_ID);
        assertThatThrownBy(() -> service.loadUserByUsername(NOT_EXIST_USER_ID))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(MessageType.USER_NOT_FOUND.getMessage());
    }
}