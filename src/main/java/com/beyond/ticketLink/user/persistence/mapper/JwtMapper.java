package com.beyond.ticketLink.user.persistence.mapper;

import com.beyond.ticketLink.user.application.domain.RefreshToken;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface JwtMapper {
    void save(JwtCreateDto jwtToken);

    Optional<RefreshToken> findByUserNo(String userNo);

    void delete(String userNo);
}
