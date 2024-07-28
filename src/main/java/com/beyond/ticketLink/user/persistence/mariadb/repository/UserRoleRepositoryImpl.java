package com.beyond.ticketLink.user.persistence.mariadb.repository;

import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.persistence.mariadb.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository{
    private final UserRoleMapper mapper;

    @Override
    public Optional<UserRole> findByRoleName(String name) {
        return mapper.findByRoleName(name);
    }
}
