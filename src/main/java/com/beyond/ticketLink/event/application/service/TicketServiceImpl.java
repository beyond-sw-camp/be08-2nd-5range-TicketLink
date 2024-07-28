package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.TicketCount;
import com.beyond.ticketLink.event.persistence.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getList(String dayEventNo) {
        return ticketRepository.getList(dayEventNo);
    }

    @Override
    public Optional<Ticket> getData(String ticketNo) {
        return ticketRepository.getData(ticketNo);
    }

    @Override
    public List<TicketCount> getCounts(String dayEventNo) {
        return ticketRepository.getCounts(dayEventNo);
    }
}