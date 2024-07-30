package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.exception.MailMessageType;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.service.UserService.FindJwtResult;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import com.beyond.ticketLink.user.exception.UserMessageType;
import com.beyond.ticketLink.user.persistence.redis.entity.ExpiredAccessToken;
import com.beyond.ticketLink.user.persistence.redis.repository.ExpiredAccessTokenRepository;
import com.beyond.ticketLink.user.persistence.mariadb.repository.UserRepository;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.beyond.ticketLink.user.application.service.UserService.LogoutCommand;
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

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ExpiredAccessTokenRepository expiredAccessTokenRepository;

    @Test
    @Transactional
    @DisplayName("[register] success case")
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

        // when & then
        assertThatCode(() -> service.register(registerRequest))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[register] error case#2 id duplicated")
    void register_shouldRegisterThrowExceptionWithConflict() {
        String dummyId = "dummyUserA";
        String dummyPwd = "1234";
        String dummyName = "testUserA";
        String dummyEmail = "testUserA@beyond.com";

        UserCreateRequest registerRequest =
                new UserCreateRequest(dummyId, dummyPwd, dummyName, dummyEmail);

        // then
        assertThatThrownBy(() -> service.register(registerRequest))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(UserMessageType.DUPLICATE_USER_ID.getMessage());
    }

    @Test
    @DisplayName("[register] error case#3 email unauthorized")
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
                .hasMessage(MailMessageType.EMAIL_UNAUTHORIZED.getMessage());
    }

    @Test
    @DisplayName("[checkIdDuplicated] not duplicated")
    void checkIdDuplicated_shouldCheckIdDuplicatedSuccessfully() {
        // given
        String notExistId = "notExistId1233";

        // then
        assertThatCode(() -> service.checkIdDuplicated(notExistId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[checkIdDuplicated] duplicated")
    void checkIdDuplicated_shouldCheckIdDuplicatedThrowExceptionWithNotFound() {
        // given
        String existId = "dummyUserA";

        // then
        assertThatThrownBy(() -> service.checkIdDuplicated(existId))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(UserMessageType.DUPLICATE_USER_ID.getMessage());
    }


    @Test
    @Transactional
    @DisplayName("[login] success case")
    void login_shouldLoginSuccessfullyWithJwtTokenSaved() {
        // given
        String dummyId = "dummyUserA";
        String dummyPw = "test1234!";
        String dummyUserNo = "DUMMYA";

        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, dummyPw);
        // when
        FindJwtResult jwtToken = service.login(loginRequest);

        String parsedUsername = jwtUtil.getUsername(jwtToken.getAccessToken());

        // then
        assertThat(parsedUsername).isEqualTo(loginRequest.id());
    }

    @Test
    @DisplayName("[login] error case #3 invalid password")
    void login_shouldLoginThrowErrorWithPasswordInvalid() {
        // given
        String dummyId = "dummyUserA";
        String invalidPw = "53453434";

        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, invalidPw);
        // when

        // then
        assertThatThrownBy(() -> service.login(loginRequest))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(UserMessageType.INVALID_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("[login] error case #2 invalid id")
    void login_shouldLoginThrowErrorWithIdNotfound() {
        // given
        String dummyId = "flsdjafldsjk";
        String invalidPw = "53453434";

        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, invalidPw);
        // when

        // then
        assertThatThrownBy(() -> service.login(loginRequest))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(UserMessageType.USER_NOT_FOUND.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("[logout] success case")
    void logout_shouldLogoutSuccessfully() {
        // given
        String dummyId = "dummyUserA";
        String dummyPw = "test1234!";
        String dummyUserNo = "DUMMYA";

        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, dummyPw);

        FindJwtResult jwtResult = service.login(loginRequest);
        String accessToken = jwtResult.getAccessToken();
        // when & then
        assertThatCode(() -> service.logout(
                LogoutCommand.builder()
                .userNo(dummyUserNo)
                .accessToken(accessToken)
                .build()
                )
        ).doesNotThrowAnyException();



        Optional<ExpiredAccessToken> expiredAccessToken = expiredAccessTokenRepository.findById(accessToken);
        assertThat(expiredAccessToken.isPresent()).isTrue();
        assertThat(expiredAccessToken.get().getAccessToken()).isEqualTo(accessToken);
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