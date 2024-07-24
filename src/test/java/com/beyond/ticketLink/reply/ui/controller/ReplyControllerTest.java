package com.beyond.ticketLink.reply.ui.controller;

import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.dummy.DummyBoard;
import com.beyond.ticketLink.dummy.DummyReply;
import com.beyond.ticketLink.reply.ui.requestbody.ReplyCreateRequest;
import com.beyond.ticketLink.user.application.mock.WithTicketLinkMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Sql(
        scripts = {
                "/test_sql/user/init.sql",
                "/test_sql/event/init.sql",
                "/test_sql/board/init.sql",
                "/test_sql/reply/init.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = {
                "/test_sql/reply/delete.sql",
                "/test_sql/board/delete.sql",
                "/test_sql/event/delete.sql",
                "/test_sql/user/delete.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
class ReplyControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    @WithTicketLinkMockUser(userNo = "DM_U_03") void modifyReply_shouldModifyReplyRespondWith200() throws Exception {
        // given
        ReplyCreateRequest updateRequest = new ReplyCreateRequest("update request");
        // when
        ResultActions perform200 = mvc.perform(put("/api/v1/boards/" + DummyBoard.DM_B_04.name() + "/reply/" + DummyReply.DM_R_04.name())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
                .header("Authorization", "qweqwe")
        );
        // then
        perform200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.uptDate").value(equalTo(LocalDate.now().toString())));
    }

    @Test
    @Transactional
    @WithTicketLinkMockUser(userNo = "DM_U_03") void modifyReply_shouldModifyReplyRespondWith400() throws Exception {
        // given
        ReplyCreateRequest updateRequest = new ReplyCreateRequest("");
        // when
        ResultActions perform400 = mvc.perform(put("/api/v1/boards/" + DummyBoard.DM_B_04.name() + "/reply/" + DummyReply.DM_R_04.name())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
                .header("Authorization", "qweqwe")
        );
        // then
        perform400.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].errorType").value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.name())))
                .andExpect(jsonPath("$.errors[0].errorMessage").value(equalTo(CommonMessageType.ARGUMENT_NOT_VALID.getMessage())));
    }

    @Test
    @Transactional
    @WithTicketLinkMockUser(userNo = "DM_U_03") void deleteReply_shouldDeleteReplyRespond200() throws Exception {
        // given

        // when
        ResultActions perform200 = mvc.perform(delete("/api/v1/boards/" + DummyBoard.DM_B_04.name() + "/reply/" + DummyReply.DM_R_04.name())
                .header("Authorization", "qweqwe")
        );
        // then
        perform200.andExpect(status().isOk());
    }

}