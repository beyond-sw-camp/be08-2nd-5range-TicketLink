package com.beyond.ticketLink.user.ui.controller;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.ui.requestbody.CheckDuplicateIdRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    public static final String VALID_ID = "validId";
    public static final String VALID_PW = "qwer1234!";

    public static final String SHOR_ID = "q";
    public static final String SHORT_PW = "q";

    public static final String LONG_ID = "qwerqqweqwewqewqewqeqweqwe";
    public static final String LONG_PW = "qwerqqweqwewqewqewqeqweqwe";

    public static final String DUMMY_NAME = "createUser";
    public static final String DUMMY_EMAIL_SUFFIX = "@beyond.com";

    public static final String DUMMY_USER_A_ID = "dummyUserA";

    public static final String DUPLICATE_ID = "dummyUserA";

    @Test
    @DisplayName("회원가입 요청 테스트")
    @Transactional
    void registerUser() throws Exception {
        // given
        UserCreateRequest REQUEST_200 = new UserCreateRequest(VALID_ID, VALID_PW, DUMMY_NAME + 1, VALID_ID + DUMMY_EMAIL_SUFFIX);
        UserCreateRequest REQUEST_400_SHORT_ID_LENGTH = new UserCreateRequest(SHOR_ID, VALID_PW , DUMMY_NAME + 2, SHOR_ID + DUMMY_EMAIL_SUFFIX);
        UserCreateRequest REQUEST_400_SHORT_PW_LENGTH = new UserCreateRequest(VALID_ID + 2, SHORT_PW, DUMMY_NAME + 3, VALID_ID + 2 + DUMMY_EMAIL_SUFFIX);
        UserCreateRequest REQUEST_400_LONG_ID_LENGTH = new UserCreateRequest(LONG_ID, VALID_PW, DUMMY_NAME + 4, LONG_ID + DUMMY_EMAIL_SUFFIX);
        UserCreateRequest REQUEST_400_LONG_PW_LENGTH = new UserCreateRequest(VALID_ID + 3, LONG_PW, DUMMY_NAME + 5, VALID_ID + 3 + DUMMY_EMAIL_SUFFIX);

        // when
        ResultActions PERFORM_200 = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_200))
        );
        ResultActions PERFORM_400_SHORT_ID_LENGTH = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_400_SHORT_ID_LENGTH))
        );
        ResultActions PERFORM_400_SHORT_PW_LENGTH = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_400_SHORT_PW_LENGTH))
        );
        ResultActions PERFORM_400_LONG_ID_LENGTH = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_400_LONG_ID_LENGTH))
        );
        ResultActions PERFORM_400_LONG_PW_LENGTH = mvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_400_LONG_PW_LENGTH))
        );

        // then
        PERFORM_200.andExpect(status().isCreated());

        PERFORM_400_SHORT_ID_LENGTH.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        PERFORM_400_SHORT_PW_LENGTH.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        PERFORM_400_LONG_ID_LENGTH.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        PERFORM_400_LONG_PW_LENGTH.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );


    }

    @Test
    @DisplayName("아이디 중복확인 요청 테스트")
    void checkDuplicate() throws Exception {
        // given
        final String NOT_EXIST_USER_ID = "weurioewruiowe";
        CheckDuplicateIdRequest REQUEST_200 = new CheckDuplicateIdRequest(NOT_EXIST_USER_ID);
        CheckDuplicateIdRequest REQUEST_409 = new CheckDuplicateIdRequest(DUPLICATE_ID);

        // when
        ResultActions PERFORM_200 = mvc.perform(post("/api/v1/user/check-duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_200))
        );

        ResultActions PERFORM_409 = mvc.perform(post("/api/v1/user/check-duplicate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(REQUEST_409))
        );

        // then
        PERFORM_200.andExpect(status().isOk());

        PERFORM_409.andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.DUPLICATE_USER_ID.name()))
                );
    }

    @Test
    @DisplayName("유저 조회 요청 테스트")
    void getUser() throws Exception {
        // given
        final String NOT_EXIST_USER_ID = "jrqijeiowfjioasj";

        final TicketLinkUserDetails DUMMY_USER_A = new TicketLinkUserDetails(
                "DUMMYA",
                "dummyUserA",
                "1234",
                "dummyUserA",
                "dummyA@beyond.com",
                'Y',
                "관리자"
        );

        // when
        ResultActions PERFORM_200 = mvc.perform(get("/api/v1/user/" + DUMMY_USER_A_ID));
        ResultActions PERFORM_403 = mvc.perform(get("/api/v1/user/" + NOT_EXIST_USER_ID));

        // then
        PERFORM_200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(equalTo(DUMMY_USER_A.getId())))
                .andExpect(jsonPath("$.data.name").value(equalTo(DUMMY_USER_A.getName())))
                .andExpect(jsonPath("$.data.email").value(equalTo(DUMMY_USER_A.getEmail())))
                .andExpect(jsonPath("$.data.role").value(equalTo(DUMMY_USER_A.getRole())));

        PERFORM_403.andExpect(status().isForbidden());
    }
}