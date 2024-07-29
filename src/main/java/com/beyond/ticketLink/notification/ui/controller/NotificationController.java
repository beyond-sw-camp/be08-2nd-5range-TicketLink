package com.beyond.ticketLink.notification.ui.controller;

import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.application.service.NotificationService;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Notification APIs", description = "알림 관련 API 목록")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notices/{notiNo}")
    @Operation(summary = "알림 정보 상세 조회", description = "알림 정보를 상세 조회한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Notification.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<Notification> getNoti(@PathVariable String notiNo) {
        Notification notification = notificationService.getNoti(notiNo);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/notices")
    @Operation(summary = "알림 정보 조회", description = "나의 알림 정보를 조회한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Notification.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<List<Notification>> getAll(@AuthenticationPrincipal String userNo) {
        List<Notification> notilist = notificationService.getAll(userNo);
        return ResponseEntity.ok(notilist);
    }

    // 알림 생성하기
    @PostMapping("/notices")
    public ResponseEntity<Notification> insertNoti(@RequestBody NotificationDto NotiDto) {

        Notification newNotification = notificationService.insertNoti(NotiDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newNotification);
    }

    @PutMapping("/notices/{notiNo}")
    @Operation(summary = "알림 삭제", description = "열람한 알림을 삭제한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Notification.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "본인의 알림 정보가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<Notification> updateNoti(@PathVariable String notiNo) {
        notificationService.updateNoti(notiNo);
        return ResponseEntity.ok(notificationService.getNoti(notiNo));
    }
}
