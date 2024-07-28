package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.DailyEvent;

import java.util.List;
import java.util.Optional;

public interface DayEventRepository {
    List<DailyEvent> getList(String eventNo, String eventDate);

    Optional<DailyEvent> getData(String dayEventNo);

    void insData(DailyEvent dayEvent);
}
