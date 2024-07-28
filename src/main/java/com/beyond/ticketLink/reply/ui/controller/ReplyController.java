package com.beyond.ticketLink.reply.ui.controller;

import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.common.view.ApiResponseView;
import com.beyond.ticketLink.reply.application.service.ReplyService;
import com.beyond.ticketLink.reply.persistence.dto.ReplyCreateDto;
import com.beyond.ticketLink.reply.ui.requestbody.ReplyCreateRequest;
import com.beyond.ticketLink.reply.ui.view.ReplyView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.beyond.ticketLink.reply.application.service.ReplyService.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards/{boardNo}")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    @Operation(summary = "댓글 작성", description = "댓글 작성 API - 로그인한 유저만 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ReplyCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request Body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "댓글 작성 권한이 없을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "댓글 작성 대상 게시글이 존재하지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 에러",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    public ResponseEntity<ApiResponseView<ReplyView>> addReply(
            @AuthenticationPrincipal String userNo,
            @Parameter @PathVariable String boardNo,
            @Parameter @RequestBody @Validated ReplyCreateRequest request
    ) {


        ReplyCreateCommand command = ReplyCreateCommand.builder()
                .userNo(userNo)
                .boardNo(boardNo)
                .content(request.content())
                .build();

        FindReplyResult result = replyService.createReply(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseView<>(new ReplyView(result)));
    }

    @PutMapping("reply/{replyNo}")
    @Operation(summary = "댓글 수정", description = "댓글 수정 API - 로그인한 유저만 가능, 본인이 작성한 댓글만 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ReplyCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request Body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "댓글 수정 권한이 없을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "댓글 작성 대상 게시글 또는 댓글이 존재하지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 에러",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    public ResponseEntity<ApiResponseView<ReplyView>> modifyReply(
            @AuthenticationPrincipal String userNo,
            @Parameter @PathVariable String boardNo,
            @Parameter @PathVariable String replyNo,
            @RequestBody @Validated ReplyCreateRequest request
    ) {

        ReplyUpdateCommand command = ReplyUpdateCommand.builder()
                .userNo(userNo)
                .replyNo(replyNo)
                .boardNo(boardNo)
                .content(request.content())
                .build();

        FindReplyResult result = replyService.modifyReply(command);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new ReplyView(result)));
    }

    @DeleteMapping("reply/{replyNo}")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제 API - 로그인한 유저만 가능, 본인이 작성한 댓글만 가능")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ReplyCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "댓글 삭제 권한이 없을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "댓글 작성 대상 게시글 또는 댓글이 존재하지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 에러",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    public ResponseEntity<Void> deleteReply(
            @AuthenticationPrincipal String userNo,
            @Parameter @PathVariable String boardNo,
            @Parameter @PathVariable String replyNo
    ) {

        ReplyDeleteCommand command = ReplyDeleteCommand.builder()
                .userNo(userNo)
                .replyNo(replyNo)
                .boardNo(boardNo)
                .build();

        replyService.deleteReply(command);

        return ResponseEntity.ok().build();
    }
}
