package com.beyond.ticketLink.user.ui.controller;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.ui.requestbody.CheckDuplicateIdRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private VerifiedEmailRepository verifiedEmailRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    void registerUser_shouldRegisterUserRespondWith201() throws Exception {
        // given
        String id = "validId";
        String pw = "qwer1234!";
        String name = "createUser";
        String email = id + "@beyond.com";

        UserCreateRequest request200 = new UserCreateRequest(id, pw, name, email);

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
                        .value(equalTo(MessageType.EMAIL_UNAUTHORIZED.name()))
                );
    }

    @Test
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
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400ShortPw.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400LongId.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

        perform400LongPw.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType")
                        .value(equalTo(MessageType.ARGUMENT_NOT_VALID.name()))
                );

    }

    @Test
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
    void checkDuplicate_shouldCheckDuplicateRespond403() throws Exception {
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
                        .value(equalTo(MessageType.DUPLICATE_USER_ID.name()))
                );
    }

    @Test
    void getUser_shouldGetUserRespond200() throws Exception {
        // given
        String existId = "dummyUserA";

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
        ResultActions PERFORM_200 = mvc.perform(get("/api/v1/user/" + existId));

        // then
        PERFORM_200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(equalTo(DUMMY_USER_A.getId())))
                .andExpect(jsonPath("$.data.name").value(equalTo(DUMMY_USER_A.getName())))
                .andExpect(jsonPath("$.data.email").value(equalTo(DUMMY_USER_A.getEmail())))
                .andExpect(jsonPath("$.data.role").value(equalTo(DUMMY_USER_A.getRole())));

    }

    @Test
    void getUser_shouldGetUserRespond403() throws Exception {
        // given
        String notExistId = "jrqijeiowfjioasj";

        // when
        ResultActions PERFORM_403 = mvc.perform(get("/api/v1/user/" + notExistId));

        // then
        PERFORM_403.andExpect(status().isForbidden());
    }


}