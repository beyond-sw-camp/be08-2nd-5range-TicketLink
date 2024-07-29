package com.beyond.ticketLink.coupon.persistence.repository;

import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.persistence.dto.CouponCreateDto;
import com.beyond.ticketLink.coupon.persistence.dto.CouponFindQuery;
import com.beyond.ticketLink.coupon.persistence.dto.CouponUpdateDto;
import com.beyond.ticketLink.coupon.persistence.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponMapper couponMapper;

    // 쿠폰 저장
    @Override
    public void save(CouponCreateDto couponCreateDto) {
        couponMapper.save(couponCreateDto);
    }

    // 쿠폰 번호로 쿠폰 조회
    @Override
    public Optional<Coupon> selectCouponByCouponNo(String couponNo) {
        return couponMapper.selectCouponByCouponNo(couponNo);
    }

    // 사용자의 모든 쿠폰 조회
    @Override
    public List<Coupon> selectCouponsByUserNo(String userNo, RowBounds rowBounds) {
        return couponMapper.selectCouponsByUserNo(userNo, rowBounds);
    }

    // 쿠폰 업데이트
    @Override
    public void updateCoupon(CouponUpdateDto couponUpdateDto) {
        couponMapper.updateCoupon(couponUpdateDto);
    }

    // 쿠폰 삭제
    @Override
    public void deleteCoupon(String couponNo) {
        couponMapper.deleteCoupon(couponNo);
    }

    // 쿼리 문자열로 모든 쿠폰 조회
    @Override
    public List<Coupon> selectAllCoupons(CouponFindQuery query, RowBounds rowBounds) {
        return couponMapper.selectAllCoupons(query, rowBounds);
    }
}
