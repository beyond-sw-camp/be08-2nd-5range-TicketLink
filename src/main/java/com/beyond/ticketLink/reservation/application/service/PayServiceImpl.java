package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.persistence.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {
    private final AutoNoRepository autoNoRepository;
    private final PayRepository payRepository;

    @Override
    public List<PayInfo> getList() {
        return payRepository.getList();
    }

    @Override
    public Optional<PayInfo> getData(String payNo) {
        return payRepository.getData(payNo);
    }

    @Override
    public PayInfo insData(PayInfo payInfo) {
        String payNo = autoNoRepository.getData("tb_payinfo");

        payInfo.setPayNo(payNo);
        payInfo.setPayment('C');
        payInfo.setUserNo("US00000004");

        payRepository.insData(payInfo);

        return payInfo;
    }
}