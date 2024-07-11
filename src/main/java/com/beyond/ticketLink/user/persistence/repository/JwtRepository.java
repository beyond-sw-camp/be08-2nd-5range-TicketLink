package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.application.domain.JwtToken;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;

import java.util.Optional;

public interface JwtRepository {

    void save(JwtCreateDto jwtToken);

    Optional<JwtToken> findByUserNo(String userNo);
}
