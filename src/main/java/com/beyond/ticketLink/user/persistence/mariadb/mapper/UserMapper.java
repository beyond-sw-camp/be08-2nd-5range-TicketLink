package com.beyond.ticketLink.user.persistence.mariadb.mapper;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.persistence.mariadb.dto.UserCreateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<TicketLinkUserDetails> findUserById(String id);

    Optional<TicketLinkUserDetails> selectUserByUserNo(String userNo);

    void save(UserCreateDto user);
}
