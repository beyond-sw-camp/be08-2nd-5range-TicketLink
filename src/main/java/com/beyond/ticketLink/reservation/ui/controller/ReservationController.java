package com.beyond.ticketLink.reservation.ui.controller;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.application.service.TicketService;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.application.service.PayService;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;
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
public class ReservationController {
    private final TicketService ticketService;
    private final PayService payService;

    // 선택한 행사의 티켓(좌석) 조회
    @GetMapping("/res/{dayEventNo}")
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable("dayEventNo") String dayEventNo) {
        List<Ticket> tickets = ticketService.getList(dayEventNo);

        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    // 결제 및 예약 정보 생성
    @PostMapping("/res/{dayEventNo}")
    public ResponseEntity<PayInfo> register(@PathVariable String dayEventNo, @RequestBody PayDto payDto, @AuthenticationPrincipal String userNo) {
        PayInfo newPayInfo = payService.insData(dayEventNo, payDto, userNo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPayInfo);
    }

    // 결제(예약) 정보 조회
    @GetMapping("/res/myInfo")
    public ResponseEntity<List<PayInfo>> getPayList(@AuthenticationPrincipal String userNo) {
        return ResponseEntity.status(HttpStatus.OK).body(payService.getList(userNo));
    }

    // 결제(예약) 정보 상세 조회
    @GetMapping("/res/myInfo/{payNo}")
    public ResponseEntity<PayInfo> getPayInfo(@PathVariable String payNo) {
        return ResponseEntity.status(HttpStatus.OK).body(payService.getData(payNo).get());
    }
    
    // 결제(예약) 정보 수정
    @PutMapping("/res/myInfo/{payNo}")
    public ResponseEntity<PayInfo> update(@PathVariable String payNo){
        return null;
    }
}
