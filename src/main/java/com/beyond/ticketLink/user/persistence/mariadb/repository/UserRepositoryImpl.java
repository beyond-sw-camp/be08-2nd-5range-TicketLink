package com.beyond.ticketLink.user.persistence.mariadb.repository;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.persistence.mariadb.dto.UserCreateDto;
import com.beyond.ticketLink.user.persistence.mariadb.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper mapper;

    @Override
    public Optional<TicketLinkUserDetails> findUserById(String id) {
        return mapper.findUserById(id);
    }

    @Override
    public void save(UserCreateDto user) {
        mapper.save(user);
    }
}
