package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;

import java.util.List;
import java.util.Optional;

public interface PayService {
    List<PayInfo> getList();

    Optional<PayInfo> getData(String payNo);

    PayInfo insData(PayInfo payInfo);
}
