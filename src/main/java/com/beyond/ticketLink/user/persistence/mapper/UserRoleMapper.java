package com.beyond.ticketLink.user.persistence.mapper;

import com.beyond.ticketLink.user.application.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRoleMapper {
    Optional<UserRole> findByRoleName(String name);
}
