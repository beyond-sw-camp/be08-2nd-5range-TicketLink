package com.beyond.ticketLink.user.persistence.mariadb.repository;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.persistence.mariadb.dto.UserCreateDto;

import java.util.Optional;

public interface UserRepository {

    Optional<TicketLinkUserDetails> findUserById(String id);

    Optional<TicketLinkUserDetails> selectUserByUserNo(String userNo);

    void save(UserCreateDto user);

}
