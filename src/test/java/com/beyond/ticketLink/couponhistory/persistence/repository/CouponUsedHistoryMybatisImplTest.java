package com.beyond.ticketLink.couponhistory.persistence.repository;

import com.beyond.ticketLink.couponhistory.application.domain.CouponUsedHistory;
import com.beyond.ticketLink.couponhistory.persistence.dto.CouponUsedHistoryCreateDto;
import com.beyond.ticketLink.dummy.DummyCoupon;
import com.beyond.ticketLink.dummy.DummyPayInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Sql(
        scripts = {
                "/test_sql/user/init.sql",
                "/test_sql/coupon/init.sql",
                "/test_sql/payinfo/init.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = {
                "/test_sql/payinfo/delete.sql",
                "/test_sql/user/delete.sql",
                "/test_sql/coupon/delete.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
@Slf4j
@SpringBootTest
class CouponUsedHistoryMybatisImplTest {

    @Autowired
    private CouponUsedHistoryRepository couponUsedHistoryRepository;

    @Test
    @Transactional
    void saveCouponUsedHistory_shouldSaveSuccessfully() {
        // given
        CouponUsedHistoryCreateDto createDto = new CouponUsedHistoryCreateDto(
                "test",
                Date.valueOf(LocalDate.now()),
                DummyCoupon.DM_C_01.name(),
                DummyPayInfo.DM_P_01.name()
        );
        // when & then
        assertThatCode(() -> couponUsedHistoryRepository.saveCouponUsedHistory(createDto))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    void selectCouponUsedHistoryByCouponNo_shouldReturnCorrectHistory() {
        // given
        CouponUsedHistoryCreateDto createDto = new CouponUsedHistoryCreateDto(
                "test",
                Date.valueOf(LocalDate.now()),
                DummyCoupon.DM_C_01.name(),
                DummyPayInfo.DM_P_01.name()
        );
        couponUsedHistoryRepository.saveCouponUsedHistory(createDto);
        // when
        Optional<CouponUsedHistory> result = couponUsedHistoryRepository.selectCouponUsedHistoryByCouponNo(createDto.getCouponNo());
        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getCoupon()).isNotNull();
        assertThat(result.get().getPayInfo()).isNotNull();
        log.info(result.get().toString());
    }
}