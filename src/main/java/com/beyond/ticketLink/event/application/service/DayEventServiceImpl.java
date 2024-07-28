package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.persistence.repository.DayEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DayEventServiceImpl implements DayEventService {
    private final DayEventRepository dayEventRepository;

    @Override
    public List<DailyEvent> getList(String eventNo, String eventDate) {
        return dayEventRepository.getList(eventNo, eventDate);
    }

    @Override
    public Optional<DailyEvent> getData(String dayEventNo) {
        return dayEventRepository.getData(dayEventNo);
    }
}