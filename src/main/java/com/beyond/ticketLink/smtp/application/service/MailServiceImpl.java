package com.beyond.ticketLink.smtp.application.service;

import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.exception.MailMessageType;
import com.beyond.ticketLink.smtp.persistence.entity.VerificationCode;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerificationCodeRepository;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationCodeRequest;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private static final String MAIL_SUBJECT = "티켓 링크 이메일 인증코드";

    public static final Long VERIFICATION_EXPIRED_TIME = 300L;

    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    private final VerifiedEmailRepository verifiedEmailRepository;

    @Override
    @Transactional
    public void sendMail(EmailVerificationRequest request) {

        final String SENDER_EMAIL_ADDRESS = "yonginfren@gmail.com";

        log.info("sender = {}", SENDER_EMAIL_ADDRESS);
        // html 형식으로 내용을 첨부하기 위한 객체
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // 송신자 메일 설정
            message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
            // 인증 코드 생성
            String verificationCode = generateVerificationCode();

            // 현재 이메일과 인증코드 레디스에 저장
            verificationCodeRepository.save(
                    VerificationCode.builder()
                    .email(request.email())
                    .code(verificationCode)
                    .ttl(VERIFICATION_EXPIRED_TIME)
                    .build()
            );

            // 메일 내용 html로 생성
            String htmlContent = generateEmailContent(verificationCode);
            // 수신자 이메일 주소 설정
            message.setRecipients(MimeMessage.RecipientType.TO, request.email());
            // 메일 제목 설정
            message.setSubject(MAIL_SUBJECT);
            // 메일 내용 설정
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // 메일 전송
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new TicketLinkException(CommonMessageType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void verifyEmail(EmailVerificationCodeRequest request) {
        String verificationCode = request.verificationCode();

        VerificationCode verified = verificationCodeRepository.findByCode(verificationCode)
                .orElseThrow(() -> new TicketLinkException(MailMessageType.VERIFICATION_CODE_INVALID));

        // 이메일 인증 성공 했을 경우
        // 인증된 이메일 저장소에 저장
        verifiedEmailRepository.save(VerifiedEmail.builder()
                .email(verified.getEmail())
                .build());

        // 인증 코드 삭제
        verificationCodeRepository.delete(verified);

    }

    private String generateVerificationCode() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int CODE_LENGTH = 8;
        final SecureRandom RANDOM = new SecureRandom();

        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();

    }

    private String generateEmailContent(String verificationCode) {
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<html>")
                .append("<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">")
                .append("<table role=\"presentation\" style=\"width: 100%; border-collapse: collapse;\">")
                .append("<tr>")
                .append("<td align=\"center\" style=\"background-color: #f7f7f7; padding: 20px;\">")
                .append("<table role=\"presentation\" style=\"width: 600px; border: 1px solid #cccccc; border-collapse: collapse; background-color: #ffffff;\">")
                .append("<tr>")
                .append("<td align=\"center\" style=\"padding: 20px 0;\">")
                .append("<h1 style=\"color: #333333;\">티켓링크 인증 코드</h1>")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style=\"padding: 20px;\">")
                .append("<p style=\"color: #333333; font-size: 16px;\">안녕하세요,</p>")
                .append("<p style=\"color: #333333; font-size: 16px;\">티켓링크 서비스를 이용해주셔서 감사합니다. 아래 인증 코드를 입력해 주세요:</p>")
                .append("<h2 style=\"color: #e74c3c; font-size: 24px;\">")
                .append(verificationCode)
                .append("</h2>")
                .append("<p style=\"color: #333333; font-size: 16px;\">감사합니다,<br>티켓링크 팀</p>")
                .append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td align=\"center\" style=\"background-color: #f7f7f7; padding: 10px;\">")
                .append("<p style=\"color: #999999; font-size: 12px;\">&copy; 2024 티켓링크. All rights reserved.</p>")
                .append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("</body>")
                .append("</html>");

        return htmlContent.toString();
    }





}
