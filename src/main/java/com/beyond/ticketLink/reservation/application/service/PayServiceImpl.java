package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.repository.TicketRepository;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.application.domain.Reservation;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;
import com.beyond.ticketLink.reservation.persistence.repository.PayRepository;
import com.beyond.ticketLink.reservation.persistence.repository.ResRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {
    private final AutoNoRepository autoNoRepository;
    private final PayRepository payRepository;
    private final TicketRepository ticketRepository;
    private final ResRepository resRepository;

    @Override
    public List<PayInfo> getList(String userNo) {
        return payRepository.getList(userNo);
    }

    @Override
    public Optional<PayInfo> getData(String payNo) {
        return payRepository.getData(payNo);
    }

    @Override
    @Transactional
    public PayInfo insData(String dayEventNo, PayDto dto, String userNo) {
        PayInfo payInfo = new PayInfo();
        String payNo = autoNoRepository.getData("tb_payinfo");
        Reservation reservation;
        String resNo;
        String status = null;

        // 결제 정보 세팅
        payInfo.setPayNo(payNo);
        payInfo.setPayment(dto.getPayment());

        // 결제 수단이 무통장입금(T)이 아닐경우 결제완료(S)로 세팅
        if (dto.getPayment() != 'T') {
            payInfo.setPayDate(LocalDate.now());
            status = "S";
            payInfo.setStatus(status.charAt(0));
        }

        payInfo.setPrice(dto.getPrice());
        payInfo.setFee(dto.getFee());
        payInfo.setDeliveryCost(dto.getDeliveryCost());
        payInfo.setDiscount(dto.getDiscount());
        payInfo.setTotalAmt(dto.getTotalAmt());
        payInfo.setUserNo(userNo);

        payRepository.insData(payInfo);

        List<Ticket> tickets = dto.getTicketList();
        String ticketNo;

        // 예약 정보 생성 로직
        for(Ticket ticket : tickets) {
            ticketNo = ticket.getTicketNo();

            // 티켓정보가 없는 상시상품일 경우 티켓정보도 동시 생성
            if (ticketNo == null) {
                ticketNo = autoNoRepository.getData("tb_ticket");
                ticket.setTicketNo(ticketNo);
                ticket.setDayEventNo(dayEventNo);

                ticketRepository.insData(ticket);
            }

            // 중복 예약 체크
            int chk = resRepository.getChkRes(ticketNo);

            if (chk > 0) {
                log.info("예약 정보가 존재하는 티켓({})입니다.", ticketNo);
                continue;
            }

            // 중복이 아닐 경우 예약 정보 세팅
            reservation = new Reservation();
            resNo = autoNoRepository.getData("tb_reservation");
            reservation.setResNo(resNo);

            if (status != null){
                reservation.setStatus(status.charAt(0));
            }

            reservation.setTicketNo(ticketNo);
            reservation.setPayNo(payNo);

            resRepository.insData(reservation);
        }

        return payInfo;
    }
}