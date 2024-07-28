package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.TicketCount;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<Ticket> getList(String dayEventNo);

    Optional<Ticket> getData(String ticketNo);

    List<TicketCount> getCounts(String dayEventNo);
}
