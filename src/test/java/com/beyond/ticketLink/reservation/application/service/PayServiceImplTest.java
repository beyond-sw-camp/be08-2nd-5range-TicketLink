package com.beyond.ticketLink.reservation.application.service;

import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.exception.ResMessageType;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;
import com.beyond.ticketLink.reservation.persistence.dto.PayListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Test
    @Transactional
    void getList() {
        // given
        String userNo = "US00000004";

        // when
        List<PayListDto> list = payService.getList(userNo);

        // then
        assertNotNull(list);
    }

    @Test
    @Transactional
    void getData() {
        // given
        String payNo = "PI000000000000000001";

        // when
        PayInfo payInfo = payService.getData(payNo).get();

        // then
        assertNotNull(payInfo);
    }

    @Test
    @Transactional
    void getDataErrorNotFound() {
        // given
        String payNo = "P0000000000000000005";

        // when,then
        assertThatThrownBy(() -> payService.getData(payNo))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(ResMessageType.RES_NOT_FOUND.getMessage());
    }

    @Test
    @Transactional
    void insData() {
        // given
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK000000000000015331");
        tickets.add(ticket);

        PayDto payDto = new PayDto(tickets, null
                , 170000L, 2000L, 0L, 0L, 172000L, 'C', null);

        // when
        PayInfo newpaInfo = payService.insData("DE000000000000000074", payDto, "US00000004");

        // then
        assertNotNull(newpaInfo);
    }

    @Test
    @Transactional
    void insDataErrorDuplicate() {
        // given
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK000000000000015331");
        tickets.add(ticket);

        PayDto payDto = new PayDto(tickets, null
                , 170000L, 2000L, 0L, 0L, 172000L, 'C', null);

        // when
        PayInfo newpaInfo = payService.insData("DE000000000000000074", payDto, "US00000004");

        // then
        assertThatThrownBy(() -> payService.insData("DE000000000000000074", payDto, "US00000004"))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(ResMessageType.RES_OPERATION_DUPLICATE.getMessage());
    }

    @Test
    @Transactional
    void updateData() {
        // given
        List<PayListDto> payInfos = payService.getList("US00000004");
        String payNo = payInfos.get(0).getPayNo();

        // when
        payService.updateData(payNo);
        PayInfo payInfo = payService.getData(payNo).get();

        // then
        assertEquals(payInfo.getStatus(), 'C');
    }

    @Test
    @Transactional
    void updateDataErrorNotFound() {
        assertThatThrownBy(() -> payService.updateData("P0000000000000000005"))
                .isInstanceOf(TicketLinkException.class)
                .hasMessage(ResMessageType.RES_NOT_FOUND.getMessage());
    }
}