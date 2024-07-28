package com.beyond.ticketLink.reservation.persistence.repository;

import com.beyond.ticketLink.reservation.application.domain.PayInfo;

import java.util.List;
import java.util.Optional;

public interface PayRepository {
    List<PayInfo> getList(String userNo);

    Optional<PayInfo> getData(String payNo);

    void insData(PayInfo payInfo);
}
