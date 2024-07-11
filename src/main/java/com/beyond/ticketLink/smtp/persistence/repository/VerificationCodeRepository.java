package com.beyond.ticketLink.smtp.persistence.repository;


import com.beyond.ticketLink.smtp.persistence.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
    Optional<VerificationCode> findByCode(String code);
}
