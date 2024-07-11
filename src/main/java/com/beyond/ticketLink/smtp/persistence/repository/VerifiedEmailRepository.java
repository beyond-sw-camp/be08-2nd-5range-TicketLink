package com.beyond.ticketLink.smtp.persistence.repository;

import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import org.springframework.data.repository.CrudRepository;

public interface VerifiedEmailRepository extends CrudRepository<VerifiedEmail, String> {
}
