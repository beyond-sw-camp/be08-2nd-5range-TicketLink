package com.beyond.ticketLink.coupon.application.service;

import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponCreateRequest;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    // 쿠폰 생성
    void createCoupon(CouponCreateRequest request);

    // 쿠폰 번호로 쿠폰 조회
    Optional<Coupon> getCouponByNo(String couponNo);

    // 사용자 번호로 쿠폰 목록 조회
    List<Coupon> getCouponsByUserNo(String userNo, int page, int size);

    // 쿠폰 업데이트
    void updateCoupon(String couponNo, CouponUpdateRequest request);

    // 쿠폰 삭제
    void deleteCoupon(String couponNo);
}
