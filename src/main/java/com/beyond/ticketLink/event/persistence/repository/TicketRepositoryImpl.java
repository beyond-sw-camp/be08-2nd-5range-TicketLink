package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketMapper ticketMapper;

    @Override
    public List<Ticket> getList(EventSearchCond eventSearch) {
        return ticketMapper.getList(eventSearch);
    }

    @Override
    public Optional<Ticket> getData(String ticketNo) {
        return ticketMapper.getData(ticketNo);
    }

    @Override
    public void insData(Ticket ticket) {
        ticketMapper.insData(ticket);
    }
}
