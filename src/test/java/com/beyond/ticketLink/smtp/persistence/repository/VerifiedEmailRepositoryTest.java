package com.beyond.ticketLink.smtp.persistence.repository;

import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class VerifiedEmailRepositoryTest {

    @Autowired
    private VerifiedEmailRepository repository;

    @Test
    @Transactional
    @DisplayName("인증 완료된 이메일 저장 테스트")
    public void save() {
        // given
        VerifiedEmail verifiedEmail = VerifiedEmail.builder()
                .email("test@beyond.com")
                .build();
        // when
        VerifiedEmail save = repository.save(verifiedEmail);
        // then
        assertThat(save.getEmail()).isEqualTo(verifiedEmail.getEmail());
    }
}