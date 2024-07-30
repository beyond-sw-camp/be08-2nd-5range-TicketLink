package com.beyond.ticketLink.couponhistory.persistence.repository;

import com.beyond.ticketLink.couponhistory.application.domain.CouponUsedHistory;
import com.beyond.ticketLink.couponhistory.persistence.dto.CouponUsedHistoryCreateDto;
import com.beyond.ticketLink.couponhistory.persistence.mapper.CouponUsedHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponUsedHistoryMybatisImpl implements CouponUsedHistoryRepository {

    private final CouponUsedHistoryMapper couponUsedHistoryMapper;

    @Override
    public void saveCouponUsedHistory(CouponUsedHistoryCreateDto createDto) {
        couponUsedHistoryMapper.saveCouponUsedHistory(createDto);
    }

    @Override
    public Optional<CouponUsedHistory> selectCouponUsedHistoryByCouponNo(String couponNo) {
        return couponUsedHistoryMapper.selectCouponUsedHistoryByCouponNo(couponNo);
    }
}
