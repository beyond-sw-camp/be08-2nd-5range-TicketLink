package com.beyond.ticketLink.smtp.application.service;

import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationCodeRequest;
import com.beyond.ticketLink.smtp.ui.requestbody.EmailVerificationRequest;

public interface MailService {

    void sendMail(EmailVerificationRequest request);

    void verifyEmail(EmailVerificationCodeRequest request);

}
