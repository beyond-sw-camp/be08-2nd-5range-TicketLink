package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;

import java.util.List;
import java.util.Optional;

public interface PayService {
    List<PayInfo> getList(String userNo);

    Optional<PayInfo> getData(String payNo);

    PayInfo insData(String dayEventNo, PayDto payDto, String userNo);
}
