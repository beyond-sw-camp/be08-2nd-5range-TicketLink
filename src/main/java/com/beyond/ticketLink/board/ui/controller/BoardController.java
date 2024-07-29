package com.beyond.ticketLink.board.ui.controller;


import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.service.BoardService;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.board.ui.requestbody.BoardUpdateRequest;
import com.beyond.ticketLink.board.ui.view.BoardView;
import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.common.view.ApiResponseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beyond.ticketLink.board.application.service.BoardService.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @Operation(summary = "게시물 작성", description = "게시글 작성 API - 로그인한 유저 혹은 관리자만 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = BoardCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    @PostMapping("/boards")
    public ResponseEntity<Void> addBoard(@RequestBody BoardCreateRequest request, @AuthenticationPrincipal String userNo) {
        // 실제 서비스 로직을 호출하여 BoardView 객체를 생성하는 부분이 필요
        // 예: BoardView boardView = boardService.createBoard(...);

        boardService.createBoard(request, userNo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시물 전체 조회", description = "게시글 전체 조회 API - 비회원, 회원 모두 요청 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Board.class))
                    ),
            }
    )
    @GetMapping("/boards")
    public ResponseEntity<ApiResponseView<List<BoardView>>> getBoardAll(
            @RequestParam(value = "bCategoryNo") Integer category,
            @RequestParam(value = "page") Integer page
    ) {
        BoardFindQuery query = new BoardFindQuery(category, page);

        // map(BoardView::new) : BoardView의 생성자를 호출하여 Board 객체를 BoardView 객체로 변환하여 매핑
        // stream().collect(Collectors.toList()) : 스트림의 결과를 다시 리스트로
        List<FindBoardResult> results = boardService.selectAllBoard(query);

        // 클라이언트에게 boardViews를 응답으로 보냄
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(
                        results.stream()
                                .map(BoardView::new)
                                .toList()
                ));
    }

    @Operation(summary = "게시물 단일 조회", description = "게시글 단일 조회 API - 비회원, 회원 모두 요청 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Board.class))
                    ),
            }
    )
    @GetMapping("/boards/{boardNo}")
    public ResponseEntity<ApiResponseView<BoardView>> getBoardDetails(
            @PathVariable String boardNo
    ) {
        BoardFindQuery query = new BoardFindQuery(boardNo);

        FindBoardResult result = boardService.getBoardByNo(query);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new BoardView(result)));
    }

    @Operation(summary = "게시물 수정", description = "게시글 수정 API - 회원, 본인이 작성한 게시물에 대해서만 요청 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Board.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "본인이 작성한 게시물이 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "수정하고자 하는 게시판이 존재하지 않는 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    @PutMapping("/boards/{boardNo}")
    public ResponseEntity<ApiResponseView<BoardView>> modifyBoard(
            @PathVariable String boardNo,
            @AuthenticationPrincipal String userNo,
            @RequestBody @Validated BoardUpdateRequest request
    ) {

        BoardUpdateCommand updateCommand = BoardUpdateCommand.builder()
                .boardNo(boardNo)
                .userNo(userNo)
                .title(request.title())
                .rating(request.rating())
                .content(request.content())
                .build();

        FindBoardResult result = boardService.modifyBoard(updateCommand);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new BoardView(result)));

    }

    @Operation(summary = "게시물 삭제", description = "게시글 삭제 API - 회원, 본인이 작성한 게시물에 대해서만 요청 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "본인이 작성한 게시물이 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "수정하고자 하는 게시판이 존재하지 않는 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    @DeleteMapping("/boards/{boardNo}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable String boardNo,
            @AuthenticationPrincipal String userNo
    ) {
        BoardDeleteCommand deleteCommand = BoardDeleteCommand.builder()
                .boardNo(boardNo)
                .userNo(userNo)
                .build();

        boardService.deleteBoard(deleteCommand);

        return ResponseEntity.ok().build();
    }

}