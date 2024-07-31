package com.beyond.ticketLink.reservation.persistence.mapper;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.persistence.dto.PayListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PayMapper {
    List<PayListDto> getList(String userNo);

    Optional<PayInfo> getData(String payNo);

    void insData(PayInfo payInfo);

    void uptData(String payNo);
}
