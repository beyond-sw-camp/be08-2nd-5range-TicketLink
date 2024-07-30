package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.DayEventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventUpdateDto;
import com.beyond.ticketLink.event.persistence.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private final EventMapper eventMapper;

    @Override
    public List<Event> getList(EventSearchCond eventSearch) {
        return eventMapper.getList(eventSearch);
    }

    @Override
    public Optional<Event> getData(String eventNo, DayEventSearchCond dto) {
        return eventMapper.getData(eventNo, dto);
    }

    @Override
    public Event insData(Event event) {
        eventMapper.insData(event);

        return event;
    }

    @Override
    public void uptData(String eventNo, EventUpdateDto updateParam) {
        eventMapper.uptData(eventNo, updateParam);
    }
}
