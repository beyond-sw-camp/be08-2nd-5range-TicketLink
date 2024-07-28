package com.beyond.ticketLink.event.persistence.repository;

import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.persistence.mapper.DayEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DayEventRepositoryImpl implements DayEventRepository {
    private final DayEventMapper dayEventMapper;

    @Override
    public List<DailyEvent> getList(String eventNo, String eventDate) {
        return dayEventMapper.getList(eventNo, eventDate);
    }

    @Override
    public Optional<DailyEvent> getData(String dayEventNo) {
        return dayEventMapper.getData(dayEventNo);
    }

    @Override
    public void insData(DailyEvent dayEvent) {
        dayEventMapper.insData(dayEvent);
    }
}
