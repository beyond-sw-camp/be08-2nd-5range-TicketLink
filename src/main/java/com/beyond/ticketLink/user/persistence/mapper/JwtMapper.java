package com.beyond.ticketLink.user.persistence.mapper;

import com.beyond.ticketLink.user.application.domain.JwtToken;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface JwtMapper {
    void save(JwtCreateDto jwtToken);

    Optional<JwtToken> findByUserNo(String userNo);

    void delete(String userNo);
}
