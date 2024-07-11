package com.beyond.ticketLink.smtp.persistence.repository;

import com.beyond.ticketLink.smtp.persistence.entity.VerificationCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class VerificationCodeRepositoryTest {

    @Autowired
    private VerificationCodeRepository repository;

    public static final Long _5_MIN = 300L;

    @Test
    @Transactional
    @DisplayName("인증코드 저장 테스트")
    public void save() {
        // given
        VerificationCode codeA = VerificationCode.builder()
                .email("beyond123@gamil.com")
                .code("test")
                .ttl(_5_MIN)
                .build();
        // when
        repository.save(codeA);
        Optional<VerificationCode> byId = repository.findById(codeA.getEmail());
        // then
        assertThat(byId.isPresent()).isTrue();
        log.info("id = {} code = {}", byId.get().getEmail(), byId.get().getCode());
        assertThat(byId.get().getCode()).isEqualTo(codeA.getCode());

    }
}