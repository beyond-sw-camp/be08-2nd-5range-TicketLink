package com.beyond.ticketLink.user.ui.controller;

import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.smtp.exception.MailMessageType;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.application.mock.WithTicketLinkMockUser;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.exception.UserMessageType;
import com.beyond.ticketLink.user.ui.requestbody.CheckDuplicateIdRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.beyond.ticketLink.user.application.service.UserService.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private VerifiedEmailRepository verifiedEmailRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    @DisplayName("[register] success case")
    void registerUser_shouldRegisterUserRespondWith201() throws Exception {
        // given
        String id = "validId";
        String pw = "qwer1234!";
        String name = "createUser";
        String email = id + "@beyond.com";

        UserCreateRequest request200 = new UserCreateRequest(id, pw, name, email);

        // 이메일 인증 완료 처리
        verifiedEmailRepository.save(VerifiedEmail.builder()
                .email(email)
                .build()
        );

        // when
        ResultActions perform200 = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request200))
        );

        // then
        perform200.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("[register] error case #1 - bad request format")
    void registerUser_shouldRegisterUserRespondWith400() throws Exception {
        // given
        String id = "validId";
        String pw = "qwer1234!";
        String name = "createUser";
        String emailSuffix = "@beyond.com";

        String shortId = "q";
        String shortPw = "q";

        String longId = "qwerqqweqwewqewqewqeqweqwe";
        String longPw = "qwerqqweqwewqewqewqeqweqwe";

        UserCreateRequest request400ShortId = new UserCreateRequest(shortId, pw, name + 1, shortId + emailSuffix);
        UserCreateRequest request400ShortPw = new UserCreateRequest(id + 1, shortPw, name + 2, id + 1 + emailSuffix);
        UserCreateRequest request400LongId = new UserCreateRequest(longId, pw, name + 3, longId + emailSuffix);
        UserCreateRequest request400LongPw = new UserCreateRequest(id + 2, longPw, name + 4, id + 2 + emailSuffix);

        // when
        ResultActions perform400ShortId = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request400ShortId))
        );

        ResultActions perform400ShortPw = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request400ShortPw))
        );

        ResultActions perform400LongId = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request400LongId))
        );

        ResultActions perform400LongPw = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request400LongPw))
        );


        // then
        perform400ShortId.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400ShortPw.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400LongId.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400LongPw.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name()))
                );

    }

    @Test
    @DisplayName("[register] error case #2 - duplicated id")
    @Transactional
    void registerUser_shouldRegisterUserRespondWith409() throws Exception {
        // given
        String id = "dummyUserA";
        String pw = "qwer1234!";
        String name = "createUser";
        String email = id + "dummyUserA@beyond.com";

        UserCreateRequest request409 = new UserCreateRequest(id, pw, name, email);

        // 이메일 인증 완료 처리
        verifiedEmailRepository.save(VerifiedEmail.builder()
                .email(email)
                .build()
        );

        // when
        ResultActions perform409 = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request409))
        );

        // then
        perform409.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(UserMessageType.DUPLICATE_USER_ID.name()))
                );
    }

    @Test
    @DisplayName("[register] error case #3 - unauthorized email")
    void registerUser_shouldRegisterUserRespondWith401() throws Exception {
        // given
        String id = "validId";
        String pw = "qwer1234!";
        String name = "createUser";
        String email = id + "@beyond.com";

        UserCreateRequest request401 = new UserCreateRequest(id, pw, name, email);

        // when
        ResultActions perform401 = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request401))
        );

        // then
        perform401.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MailMessageType.EMAIL_UNAUTHORIZED.name()))
                );
    }

    @Test
    @Transactional
    @DisplayName("[login] success case")
    void login_shouldLoginRespondWith200() throws Exception {
        // given
        String dummyId = "dummyUserA";
        String dummyPw = "test1234!";
        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, dummyPw);
        // when
        ResultActions perform200 = mvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        );
        // then
        perform200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    @DisplayName("[login] error case #1 - bad request format")
    void login_shouldLoginRespondWith400() throws Exception {
        // given
        String dummyId = "dummyUserA";
        String empty = "";
        String dummyPw = "test1234!";

        UserLoginRequest loginRequestWithEmptyId = new UserLoginRequest(empty, dummyPw);
        UserLoginRequest loginRequestWithEmptyPw = new UserLoginRequest(dummyId, empty);

        // when
        ResultActions perform400WithEmptyId = mvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequestWithEmptyId))
        );

        ResultActions perform400WithEmptyPw = mvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequestWithEmptyPw))
        );

        // then
        perform400WithEmptyId.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400WithEmptyPw.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name())
                        ));
    }

    @Test
    @DisplayName("[login] error case #2 - invalid password")
    void login_shouldLoginRespondWith404InvalidId() throws Exception {
        // given
        String dummyId = "xcvfsd";
        String notExistPw = "41412dscssd";
        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, notExistPw);

        // when
        ResultActions perform404 = mvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        );

        // then
        perform404.andExpect(status().isNotFound());
        perform404.andExpect(jsonPath("$.errors[0].errorType")
                .value(equalTo(UserMessageType.USER_NOT_FOUND.name()))
        );
    }

    @Test
    @DisplayName("[login] error case #3 - invalid id")
    void login_shouldLoginRespondWith400InvalidPw() throws Exception {
        // given
        String dummyId = "dummyUserA";
        String notExistPw = "41412dscssd";
        UserLoginRequest loginRequest = new UserLoginRequest(dummyId, notExistPw);

        // when
        ResultActions perform404 = mvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))
        );

        // then
        perform404.andExpect(status().isBadRequest());
        perform404.andExpect(jsonPath("$.errors[0].errorType")
                .value(equalTo(UserMessageType.INVALID_PASSWORD.name()))
        );
    }

    @Test
    @Transactional
    @WithTicketLinkMockUser
    @DisplayName("[logout] success case")
    void logout_logoutShouldRespond200() throws Exception {
        // given
        String dummyId = "dummyUserA";
        String dummyPw = "test1234!";
        FindJwtResult jwtResult = userService.login(new UserLoginRequest(dummyId, dummyPw));
        // when
        ResultActions perform200 = mvc.perform(post("/api/v1/user/logout")
                .header("Authorization", jwtResult.getAccessToken())
        );
        // then
        perform200.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[logout] error case without login")
    void logout_logoutShouldRespond401() throws Exception {
        // given

        // when
        ResultActions perform401 = mvc.perform(post("/api/v1/user/logout"));
        // then
        perform401.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("[checkDuplicate] success case")
    void checkDuplicate_shouldCheckDuplicateRespond200() throws Exception {
        // given
        String notExistId = "notExist1241412";
        CheckDuplicateIdRequest request200 = new CheckDuplicateIdRequest(notExistId);

        // when
        ResultActions perform200 = mvc.perform(post("/api/v1/user/check-duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request200))
        );

        // then
        perform200.andExpect(status().isOk());

    }

    @Test
    @DisplayName("[checkDuplicate] conflict id")
    void checkDuplicate_shouldCheckDuplicateRespond409() throws Exception {
        // given
        String existId = "dummyUserA";
        CheckDuplicateIdRequest request409 = new CheckDuplicateIdRequest(existId);

        // when
        ResultActions perform409 = mvc.perform(post("/api/v1/user/check-duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request409))
        );

        // then
        perform409.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(UserMessageType.DUPLICATE_USER_ID.name()))
                );
    }

    @Test
    @WithTicketLinkMockUser
    @DisplayName("[getProfile] success case")
    void getProfile_shouldGetUserRespond200() throws Exception {
        // given

        final TicketLinkUserDetails DUMMY_USER_A = new TicketLinkUserDetails(
                "DUMMYA",
                "dummyUserA",
                "1234",
                "dummyUserA",
                "dummyA@beyond.com",
                'Y',
                new UserRole(1, "관리자")

        );

        // when
        ResultActions PERFORM_200 = mvc.perform(get("/api/v1/user/profile")
                .header("Authorization", "gsgsg")
        );

        // then
        PERFORM_200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(equalTo(DUMMY_USER_A.getId())))
                .andExpect(jsonPath("$.data.name").value(equalTo(DUMMY_USER_A.getName())))
                .andExpect(jsonPath("$.data.email").value(equalTo(DUMMY_USER_A.getEmail())))
                .andExpect(jsonPath("$.data.role").value(equalTo(DUMMY_USER_A.getRole().getName())))
                .andDo(print());

    }

    @Test
    @DisplayName("[getProfile] error - without login")
    void getProfile_shouldGetUserRespond401() throws Exception {

        // when
        ResultActions perform401 = mvc.perform(get("/api/v1/user/profile"));

        // then
        perform401.andExpect(status().isUnauthorized());
    }

    @Test
    @WithTicketLinkMockUser(role="관리자")
    @DisplayName("[getUserByUserNo] success case")
    void getUserByAdmin_shouldGetUserRespond200() throws Exception {
        // given
        String dummyUserNo = "DUMMYA";

        final TicketLinkUserDetails DUMMY_USER_A = new TicketLinkUserDetails(
                "DUMMYA",
                "dummyUserA",
                "1234",
                "dummyUserA",
                "dummyA@beyond.com",
                'Y',
                new UserRole(1, "관리자")

        );

        // when
        ResultActions perform200 = mvc.perform(get("/api/v1/user/" + dummyUserNo));

        // then
        perform200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(equalTo(DUMMY_USER_A.getId())))
                .andExpect(jsonPath("$.data.name").value(equalTo(DUMMY_USER_A.getName())))
                .andExpect(jsonPath("$.data.email").value(equalTo(DUMMY_USER_A.getEmail())))
                .andExpect(jsonPath("$.data.role").value(equalTo(DUMMY_USER_A.getRole().getName())))
                .andDo(print());
    }

    @Test
    @DisplayName("[getUserByUserNo] error - not admin")
    @WithTicketLinkMockUser(role = "일반사용자")
    void getUserByAdmin_shouldGetUserRespond403() throws Exception {
        // given
        String dummyUserNo = "DUMMYA";

        // when
        ResultActions perform403 = mvc.perform(get("/api/v1/user/" + dummyUserNo));

        // then
        perform403.andExpect(status().isForbidden());
    }



}