package com.beyond.ticketLink.coupon.persistence.repository;

import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.persistence.dto.CouponCreateDto;
import com.beyond.ticketLink.coupon.persistence.dto.CouponFindQuery;
import com.beyond.ticketLink.coupon.persistence.dto.CouponUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    // 쿠폰 저장
    void save(CouponCreateDto couponCreateDto);

    // 쿠폰 번호로 쿠폰 조회
    Optional<Coupon> selectCouponByCouponNo(@Param("couponNo") String couponNo);

    // 사용자의 모든 쿠폰 조회
    List<Coupon> selectCouponsByUserNo(@Param("userNo") String userNo, RowBounds rowBounds);

    // 쿼리 문자열로 모든 쿠폰 조회
    List<Coupon> selectAllCoupons(CouponFindQuery query, RowBounds rowBounds);

    // 쿠폰 업데이트
    void updateCoupon(CouponUpdateDto couponUpdateDto);

    // 쿠폰 삭제
    void deleteCoupon(@Param("couponNo") String couponNo);
}
