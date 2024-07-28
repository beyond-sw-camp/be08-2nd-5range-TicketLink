package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.TicketCount;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    List<Ticket> getList(String dayEventNo);

    Optional<Ticket> getData(String ticketNo);

    void insData(Ticket ticket);

    List<TicketCount> getCounts(String dayEventNo);
}
