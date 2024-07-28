package com.beyond.ticketLink.user.persistence.repository;

import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.persistence.mariadb.repository.UserRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRoleRepositoryImplTest {

    @Autowired
    private UserRoleRepository repository;

    public static final String ROLE_NAME_ADMIN = "관리자";
    public static final String ROLE_NAME_USER = "일반사용자";
    public static final String ROLE_NAME_NOT_EXIST = "ROLE_NAME_NOT_EXIST";

    @Test
    @DisplayName("유저 role 검색 테스트")
    void findByRoleName() {
        // given

        // when
        Optional<UserRole> roleAdmin = repository.findByRoleName(ROLE_NAME_ADMIN);
        Optional<UserRole> roleUser = repository.findByRoleName(ROLE_NAME_USER);
        Optional<UserRole> roleNotExist = repository.findByRoleName(ROLE_NAME_NOT_EXIST);
        // then
        assertThat(roleAdmin.isPresent()).isTrue();
        assertThat(roleUser.isPresent()).isTrue();
        assertThat(roleNotExist.isEmpty()).isTrue();

        assertThat(roleAdmin.get().getName()).isEqualTo(ROLE_NAME_ADMIN);
        assertThat(roleUser.get().getName()).isEqualTo(ROLE_NAME_USER);
    }
}