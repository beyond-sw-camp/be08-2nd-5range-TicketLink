package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.application.domain.RefreshToken;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;

import java.util.Optional;

public interface JwtRepository {

    void save(JwtCreateDto jwtToken);

    Optional<RefreshToken> findByUserNo(String userNo);
}
