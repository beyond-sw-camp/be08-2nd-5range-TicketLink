package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.DailyEvent;

import java.util.List;
import java.util.Optional;

public interface DayEventService {
    List<DailyEvent> getList(String eventNo, String eventDate);

    Optional<DailyEvent> getData(String dayEventNo);
}
