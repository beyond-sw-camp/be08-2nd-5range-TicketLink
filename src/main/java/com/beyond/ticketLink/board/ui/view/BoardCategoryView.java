package com.beyond.ticketLink.board.ui.view;

import com.beyond.ticketLink.board.application.service.BoardCategoryService.FindBoardCategoryResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardCategoryView {

    @JsonProperty("bCategoryNo")
    private final int bCategoryNo;

    private final String name;

    public BoardCategoryView(FindBoardCategoryResult boardCategory) {
        this.bCategoryNo = boardCategory.getBCategoryNo();
        this.name = boardCategory.getName();
    }
}
