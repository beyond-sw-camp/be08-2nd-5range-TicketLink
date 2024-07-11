package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.persistence.repository.UserRepository;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService service;

    @Autowired
    VerifiedEmailRepository verifiedEmailRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void register_shouldRegisterSuccessfully() {
        // given
        String dummyId = "testUserA";
        String dummyPwd = "1234";
        String dummyName = "testUserA";
        String dummyEmail = "testUserA@beyond.com";

        // 회원가입 성공 조건 : 이메일 인증이 되었을 경우
        verifiedEmailRepository.save(VerifiedEmail.builder()
                .email(dummyEmail)
                .build()
        );

        UserCreateRequest registerRequest =
                new UserCreateRequest(dummyId, dummyPwd, dummyName, dummyEmail);

        // when
        service.register(registerRequest);

        // then
        Optional<TicketLinkUserDetails> user = userRepository.findUserById(dummyId);
        assertThat(user.isPresent()).isTrue();

        assertThat(user.get().getId()).isEqualTo(registerRequest.id());
        assertThat(user.get().getPassword()).isNotEqualTo(registerRequest.pw());
        assertThat(user.get().getUsername()).isEqualTo(registerRequest.name());
        assertThat(user.get().getEmail()).isEqualTo(registerRequest.email());
        assertThat(user.get().getRole()).isEqualTo("일반사용자");
    }

    @Test
    @Transactional
    void register_shouldRegisterThrowExceptionWithUnauthorizedEmail() {
        String dummyId = "testUserA";
        String dummyPwd = "1234";
        String dummyName = "testUserA";
        String dummyEmail = "testUserA@beyond.com";

        UserCreateRequest registerRequest =
                new UserCreateRequest(dummyId, dummyPwd, dummyName, dummyEmail);

        // then
        assertThatThrownBy(() -> service.register(registerRequest))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(MessageType.EMAIL_UNAUTHORIZED.getMessage());
    }

    @Test
    void checkIdDuplicated_shouldCheckIdDuplicatedSuccessfully() {
        // given
        String notExistId = "notExistId1233";

        // then
        assertThatCode(() -> service.checkIdDuplicated(notExistId))
                .doesNotThrowAnyException();
    }

    @Test
    void checkIdDuplicated_shouldCheckIdDuplicatedThrowExceptionWithNotFound() {
        // given
        String existId = "dummyUserA";

        // then
        assertThatThrownBy(() -> service.checkIdDuplicated(existId))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(MessageType.DUPLICATE_USER_ID.getMessage());
    }

    @Test
    void loadUserByUsername_shouldLoadUserByUsernameSuccessfully() {
        // given
        String DUMMY_USER_A_ID = "dummyUserA";
        // when
        UserDetails DUMMY_USER_A = service.loadUserByUsername(DUMMY_USER_A_ID);
        // then
        assertThat(DUMMY_USER_A.getUsername()).isEqualTo(DUMMY_USER_A_ID);
        final String NOT_EXIST_USER_ID = "notExistUserId";
    }

    @Test
    void loadUserByUsername_shouldLoadUserByUsernameThrowExceptionWithNotFound() {
        // given
        String notExistUserId = "notExistUserId";
        // when

        // then
        assertThatThrownBy(() -> service.loadUserByUsername(notExistUserId))
                .isInstanceOf(UsernameNotFoundException.class);
    }

}