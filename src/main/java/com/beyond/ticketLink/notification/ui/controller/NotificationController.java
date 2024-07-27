package com.beyond.ticketLink.notification.ui.controller;

import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.notification.application.domain.Notification;
import com.beyond.ticketLink.notification.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/res/Noti")
public class NotificationController {

    private final NotificationService notificationService;

    // 단일 목록 받아오기
    @GetMapping("/notice/{notiNo}")
    public ResponseEntity<Notification> getNoti(@PathVariable String notiNo) {
        Notification notification = notificationService.getNoti(notiNo).get();
        return ResponseEntity.ok(notification);
    }

    // 전체 목록 받아오기
    @GetMapping("/notice/all/{userNo}")
    public ResponseEntity<List<Notification>> selectAll(String userNo) {
        System.out.println("userNo = " + userNo);
        List<Notification> notilist = notificationService.selectAll(userNo);
        return ResponseEntity.ok(notilist);
    }

    // 알림 삭제 하기
    @PutMapping("/update/{notiNo}")
    public ResponseEntity<Notification> updateNoti(
            @PathVariable String notiNo
    ) {
        notificationService.updateNoti(notiNo);
        return ResponseEntity.ok(notificationService.getNoti(notiNo).orElse(null));
    }

//    @PutMapping("/{subject-no}")
//    public ResponseEntity<SubjectResponseDto> udpateSubject(
//            @PathVariable("subject-no") String subjectNo,
//            @RequestBody SubjectRequestDto requestDto) {
//        Subject subject = subjectService.getSubjectBySubjectNo(subjectNo);
//
//        if (subject != null) {
//            subject.setRequestDto(requestDto);
//
//            subjectService.save(subject);
//
//            subject = subjectService.getSubjectBySubjectNo(subject.getNo());
//
//            return ResponseEntity.ok(new SubjectResponseDto(HttpStatus.OK, subject));
//        } else {
//            return ResponseEntity.ok(new SubjectResponseDto(HttpStatus.NOT_FOUND, subject));
//        }
//    }

//    @PutMapping("/{notiNo}/update")
//    public ResponseEntity<Notification> update(@PathVariable String notiNo, @RequestBody NotificationUpdateDto dto) {
//        notificationService.uptData(notiNo, dto);
//
//        return ResponseEntity.ok(notificationService.getData(notiNo).get());
//    }
}
