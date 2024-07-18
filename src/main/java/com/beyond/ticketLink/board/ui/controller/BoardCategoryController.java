package com.beyond.ticketLink.board.ui.controller;

import com.beyond.ticketLink.board.application.service.BoardCategoryService;
import com.beyond.ticketLink.board.ui.view.BoardCategoryView;
import com.beyond.ticketLink.common.view.ApiResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.beyond.ticketLink.board.application.service.BoardCategoryService.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardCategoryController {

    private final BoardCategoryService boardCategoryService;

    @GetMapping("/board-categories")
    public ResponseEntity<ApiResponseView<List<BoardCategoryView>>> getAllCategory() {

        List<FindCategoryResult> allCategory = boardCategoryService.getAllCategory();

        return ResponseEntity.ok()
                .body(new ApiResponseView<>(allCategory.stream()
                                .map(BoardCategoryView::new)
                                .toList()
                        )
                );
    }

}
