package com.beyond.ticketLink.couponhistory.persistence.repository;

import com.beyond.ticketLink.couponhistory.application.domain.CouponUsedHistory;
import com.beyond.ticketLink.couponhistory.persistence.dto.CouponUsedHistoryCreateDto;

import java.util.Optional;

public interface CouponUsedHistoryRepository {

    void saveCouponUsedHistory(CouponUsedHistoryCreateDto createDto);

    Optional<CouponUsedHistory> selectCouponUsedHistoryByCouponNo(String couponNo);

}
