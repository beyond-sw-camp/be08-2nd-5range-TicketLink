package com.beyond.ticketLink.reservation.persistence.repository;

import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.exception.ResMessageType;
import com.beyond.ticketLink.reservation.persistence.dto.PayListDto;
import com.beyond.ticketLink.reservation.persistence.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PayRepositoryImpl implements PayRepository {
    private final PayMapper payMapper;

    @Override
    public List<PayListDto> getList(String userNo) {
        return payMapper.getList(userNo);
    }

    @Override
    public Optional<PayInfo> getData(String payNo) {
        if (payMapper.getData(payNo).isEmpty()){
            throw new TicketLinkException(ResMessageType.RES_NOT_FOUND);
        }

        return payMapper.getData(payNo);
    }

    @Override
    public void insData(PayInfo payInfo) {
        payMapper.insData(payInfo);
    }

    @Override
    public void uptData(String payNo) {
        payMapper.uptData(payNo);
    }
}
