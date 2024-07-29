package com.beyond.ticketLink.coupon.application.service;


import com.beyond.ticketLink.coupon.ui.requestbody.CouponCreateRequest;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponUpdateRequest;
import com.beyond.ticketLink.dummy.DummyCoupon;
import com.beyond.ticketLink.dummy.DummyUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

@Slf4j
@SpringBootTest
@Sql(
        scripts = {
                "/test_sql/user/init.sql",
                "/test_sql/coupon/init.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
        scripts = {
                "/test_sql/user/delete.sql",
                "/test_sql/coupon/delete.sql"
        },
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    @Test
    @Transactional
    void createCoupon_shouldCreateSuccessfully() {
        // given
        CouponCreateRequest request = new CouponCreateRequest("쿠폰 생성 서비스", 10, DummyUser.DM_U_04.name(), LocalDate.now());
        // when & then
        assertThatCode(() -> couponService.createCoupon(request))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    void updateCoupon_shouldUpdateSuccessfully() {
        // given
        CouponUpdateRequest updateDto = new CouponUpdateRequest("수정 테스트", 30, LocalDate.now());
        String couponNo = DummyCoupon.DM_C_01.name();
        // when & then
        assertThatCode(() -> couponService.updateCoupon(couponNo, updateDto))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    void deleteCoupon_shouldDeleteSuccessfully() {
        // given
        String couponNo = DummyCoupon.DM_C_01.name();
        // when & then
        assertThatCode(() -> couponService.deleteCoupon(couponNo))
                .doesNotThrowAnyException();
    }

}