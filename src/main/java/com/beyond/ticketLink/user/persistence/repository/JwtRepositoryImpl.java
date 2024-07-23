package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.application.domain.RefreshToken;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;
import com.beyond.ticketLink.user.persistence.mapper.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtMapper jwtMapper;

    @Override
    public void save(JwtCreateDto jwtToken) {
        jwtMapper.save(jwtToken);
    }

    @Override
    public Optional<RefreshToken> findByUserNo(String userNo) {
        return mapper.findByUserNo(userNo);
    }

    @Override
    public void delete(String userNo) {
        jwtMapper.delete(userNo);
    }
}
