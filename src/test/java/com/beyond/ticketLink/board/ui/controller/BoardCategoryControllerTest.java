package com.beyond.ticketLink.board.ui.controller;

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardCategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void getAllCategory() throws Exception {
        // given

        // when
        ResultActions perform200 = mvc.perform(get("/api/v1/board-categories"));
        // then
        perform200.andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[*].bCategoryNo").exists())
                .andExpect(jsonPath("$.data[*].name").exists());
    }
}