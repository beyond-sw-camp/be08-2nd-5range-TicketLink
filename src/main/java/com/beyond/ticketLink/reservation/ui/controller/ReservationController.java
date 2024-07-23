package com.beyond.ticketLink.reservation.ui.controller;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.service.EventService;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.application.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket/res")
public class ReservationController {
    private final PayService payService;

    @GetMapping
    public ResponseEntity<List<PayInfo>> getList(@RequestBody EventSearchCond cond) {
        List<PayInfo> list = payService.getList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{payNo}")
    public ResponseEntity<PayInfo> getData(@PathVariable String payNo) {
        PayInfo payInfo = payService.getData(payNo).get();

        return ResponseEntity.ok(payInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<PayInfo> register(@RequestBody PayInfo payInfo) {
        PayInfo newPayInfo = payService.insData(payInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPayInfo);
    }
}
