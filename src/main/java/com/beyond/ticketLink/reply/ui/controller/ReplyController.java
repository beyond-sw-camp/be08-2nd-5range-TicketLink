package com.beyond.ticketLink.reply.ui.controller;

import com.beyond.ticketLink.common.view.ApiResponseView;
import com.beyond.ticketLink.reply.application.service.ReplyService;
import com.beyond.ticketLink.reply.ui.requestbody.ReplyCreateRequest;
import com.beyond.ticketLink.reply.ui.view.ReplyView;
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
    public ResponseEntity<ApiResponseView<ReplyView>> addReply(
            @AuthenticationPrincipal String userNo,
            @PathVariable String boardNo,
            @RequestBody @Validated ReplyCreateRequest request
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
    public ResponseEntity<ApiResponseView<ReplyView>> modifyReply(
            @AuthenticationPrincipal String userNo,
            @PathVariable String boardNo,
            @PathVariable String replyNo,
            @RequestBody @Validated ReplyCreateRequest request
    ) {

        log.info("userNo = {} boardNo = {} request = {}", userNo, boardNo, request);

        ReplyUpdateCommand command = ReplyUpdateCommand.builder()
                .userNo(userNo)
                .replyNo(replyNo)
                .content(request.content())
                .build();

        FindReplyResult result = replyService.modifyReply(command);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new ReplyView(result)));
    }

    @DeleteMapping("reply/{replyNo}")
    public ResponseEntity<Void> deleteReply(
            @AuthenticationPrincipal String userNo,
            @PathVariable String boardNo,
            @PathVariable String replyNo
    ) {

        ReplyDeleteCommand command = ReplyDeleteCommand.builder()
                .userNo(userNo)
                .replyNo(replyNo)
                .build();

        replyService.deleteReply(command);

        return ResponseEntity.ok().build();
    }
}
