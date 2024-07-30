package com.beyond.ticketLink.reservation.persistence.repository;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.persistence.dto.PayListDto;

import java.util.List;
import java.util.Optional;

public interface PayRepository {
    List<PayListDto> getList(String userNo);

    Optional<PayInfo> getData(String payNo);

    void insData(PayInfo payInfo);

    void uptData(String payNo);
}
