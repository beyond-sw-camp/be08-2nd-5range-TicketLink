package com.beyond.ticketLink.reservation.persistence.mapper;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PayMapper {
    List<PayInfo> getList();

    Optional<PayInfo> getData(String payNo);

    void insData(PayInfo payInfo);
}
