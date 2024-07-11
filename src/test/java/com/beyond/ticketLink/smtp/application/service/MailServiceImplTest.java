package com.beyond.ticketLink.smtp.application.service;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.persistence.entity.VerificationCode;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerificationCodeRepository;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationCodeRequest;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class MailServiceImplTest {

    @Autowired
    private MailServiceImpl mailService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private VerificationCodeRepository verificationCodeRepository;

    @MockBean
    private VerifiedEmailRepository verifiedEmailRepository;

    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        // JavaMailSender를 모의하여 MimeMessage를 생성
        mimeMessage = new JavaMailSenderImpl().createMimeMessage();
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void sendMail_shouldSendVerificationEmail() throws Exception {
        // Given
        EmailVerificationRequest request = new EmailVerificationRequest("test@example.com");

        // When
        mailService.sendMail(request);

        // Then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        verify(verificationCodeRepository, times(1)).save(any(VerificationCode.class));
    }

    @Test
    public void verifyEmail_shouldVerifyEmailSuccessfully() {
        // Given
        String verificationCode = "12345678";
        EmailVerificationCodeRequest request = new EmailVerificationCodeRequest(verificationCode);
        VerificationCode emailVerificationCode = VerificationCode.builder()
                .email("test@example.com")
                .code(verificationCode)
                .ttl(MailServiceImpl.VERIFICATION_EXPIRED_TIME)
                .build();


        when(verificationCodeRepository.findByCode(verificationCode)).thenReturn(Optional.of(emailVerificationCode));

        // When
        mailService.verifyEmail(request);

        // Then
        verify(verifiedEmailRepository, times(1)).save(any(VerifiedEmail.class));
        verify(verificationCodeRepository, times(1)).delete(emailVerificationCode);
    }

    @Test
    void verifyEmail_shouldThrowExceptionForInvalidCode() {
        // Given
        String verificationCode = "invalid";
        EmailVerificationCodeRequest request = new EmailVerificationCodeRequest(verificationCode);

        when(verificationCodeRepository.findByCode(verificationCode)).thenReturn(Optional.empty());

        // When & Then
        TicketLinkException exception = assertThrows(TicketLinkException.class, () -> mailService.verifyEmail(request));

        assertThat(exception.getType()).isEqualTo(MessageType.VERIFICATION_CODE_INVALID.name());

    }
}