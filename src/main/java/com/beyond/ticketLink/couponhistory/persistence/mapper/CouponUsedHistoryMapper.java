package com.beyond.ticketLink.couponhistory.persistence.mapper;

import com.beyond.ticketLink.couponhistory.application.domain.CouponUsedHistory;
import com.beyond.ticketLink.couponhistory.persistence.dto.CouponUsedHistoryCreateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CouponUsedHistoryMapper {
    void saveCouponUsedHistory(CouponUsedHistoryCreateDto createDto);

    Optional<CouponUsedHistory> selectCouponUsedHistoryByCouponNo(String couponNo);
}
