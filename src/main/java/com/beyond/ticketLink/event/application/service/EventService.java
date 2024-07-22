package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getList(EventSearchCond eventSearch);

    Optional<Event> getData(String eventNo);

    Event insData(Event event);

    void uptData(String eventNo, EventDto updateParam);
}
