package com.beyond.ticketLink.user.persistence.mariadb.repository;

import com.beyond.ticketLink.user.application.domain.RefreshToken;
import com.beyond.ticketLink.user.persistence.mariadb.dto.JwtCreateDto;

import java.util.Optional;

public interface JwtRepository {

    void save(JwtCreateDto jwtToken);

    Optional<RefreshToken> findByUserNo(String userNo);

    void delete(String userNo);
}
