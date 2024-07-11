package com.beyond.ticketLink.smtp.ui.controller;

import com.beyond.ticketLink.smtp.application.service.MailService;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationCodeRequest;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MailController {

    private final MailService service;

    @GetMapping("/mail/verification-code")
    public ResponseEntity<Void> getAuthenticationNumber(@RequestBody @Validated EmailVerificationRequest request) {
        service.sendMail(request);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<Void> confirmMail(@RequestBody @Validated EmailVerificationCodeRequest request) {
        service.verifyEmail(request);
        return ResponseEntity.ok()
                .build();
    }
}
