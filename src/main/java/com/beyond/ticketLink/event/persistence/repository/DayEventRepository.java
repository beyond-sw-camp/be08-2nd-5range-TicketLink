package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;

import java.util.List;
import java.util.Optional;

public interface DayEventRepository {
    List<DailyEvent> getList(EventSearchCond eventSearch);

    Optional<DailyEvent> getData(String dayEventNo);

    void insData(DailyEvent dayEvent);
}
