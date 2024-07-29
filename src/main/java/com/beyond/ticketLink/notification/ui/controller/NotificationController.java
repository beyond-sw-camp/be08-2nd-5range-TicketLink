package com.beyond.ticketLink.notification.ui.controller;

import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.application.service.NotificationService;
import com.beyond.ticketLink.notification.persistence.dto.NotificationDto;
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
public class NotificationController {

    private final NotificationService notificationService;

    // 단일 목록 받아오기
    @GetMapping("/notices/{notiNo}")
    public ResponseEntity<Notification> getNoti(@PathVariable String notiNo) {
        Notification notification = notificationService.getNoti(notiNo);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 전체 목록 받아오기
    @GetMapping("/notices")
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

    // 알림 삭제 하기
    @PutMapping("/notices/{notiNo}")
    public ResponseEntity<Notification> updateNoti(@PathVariable String notiNo) {
        notificationService.updateNoti(notiNo);
        return ResponseEntity.ok(notificationService.getNoti(notiNo));
    }




}
