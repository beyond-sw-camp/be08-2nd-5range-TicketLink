package com.beyond.ticketLink.event.persistence.mapper;

import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.TicketCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TicketMapper {
    List<Ticket> getList(String dayEventNo);

    Optional<Ticket> getData(String ticketNo);

    void insData(Ticket ticket);

    List<TicketCount> getCountList(String dayEventNo);
}
