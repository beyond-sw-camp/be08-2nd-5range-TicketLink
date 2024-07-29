package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.DayEventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventUpdateDto;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    List<Event> getList(EventSearchCond eventSearch);

    Optional<Event> getData(String eventNo, DayEventSearchCond dto);

    Event insData(Event event);

    void uptData(String eventNo, EventUpdateDto updateParam);
}
