package com.beyond.ticketLink.coupon.persistence.repository;

import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.persistence.dto.CouponCreateDto;
import com.beyond.ticketLink.coupon.persistence.dto.CouponFindQuery;
import com.beyond.ticketLink.coupon.persistence.dto.CouponUpdateDto;
import com.beyond.ticketLink.dummy.DummyCoupon;
import com.beyond.ticketLink.dummy.DummyUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
class CouponRepositoryImplTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @Transactional
    void save_shouldSaveSuccessfully() {
        // given
        CouponCreateDto couponCreateDto = new CouponCreateDto(
                "testNo",
                "testCode",
                "testName",
                3,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                "DUMMYA"
        );
        // when & then
        assertThatCode(() -> couponRepository.save(couponCreateDto))
                .doesNotThrowAnyException();
    }

    @Test
    void selectCouponByCouponNo_shouldReturnCorrectCoupon() {
        // given
        String couponNo = DummyCoupon.DM_C_01.name();
        // when
        Optional<Coupon> result = couponRepository.selectCouponByCouponNo(couponNo);
        // then
        assertThat(result.isPresent()).isTrue();
        log.info(result.get().toString());
    }

    @Test
    void selectCouponsByUserNo_shouldReturnCorrectCoupons() {
        // given
        String userNo = DummyUser.DM_U_03.name();
        RowBounds rowBounds = new RowBounds(1, 10);
        // when
        List<Coupon> result = couponRepository.selectCouponsByUserNo(userNo, rowBounds);
        // then
        assertThat(result.isEmpty()).isFalse();
        log.info(result.toString());
    }

    @Test
    void selectAllCoupons_shouldReturnCorrectCoupons() {
        // given
        CouponFindQuery query = new CouponFindQuery(DummyCoupon.DM_C_01.name(), DummyUser.DM_U_03.name());
        // when
        List<Coupon> results = couponRepository.selectAllCoupons(query);
        // then
        assertThat(results.isEmpty()).isFalse();
        log.info(results.toString());
    }

    @Test
    @Transactional
    void updateCoupon_shouldUpdateSuccessfully() {
        // given
        CouponCreateDto couponCreateDto = new CouponCreateDto(
                "testNo",
                "testCode",
                "testName",
                3,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                "DUMMYA"
        );
        couponRepository.save(couponCreateDto);

        CouponUpdateDto updateCoupon = new CouponUpdateDto(
                couponCreateDto.getCouponNo(),
                "update Coupon",
                10,
                LocalDate.now().plusDays(10));
        // when & then
        assertThatCode(() -> couponRepository.updateCoupon(updateCoupon))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    void deleteCoupon_shouldDeleteSuccessfully() {
        // given
        String couponNo = DummyCoupon.DM_C_01.name();
        // when & then
        assertThatCode(() -> couponRepository.deleteCoupon(couponNo))
                .doesNotThrowAnyException();
    }



}