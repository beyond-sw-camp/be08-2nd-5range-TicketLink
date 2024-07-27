package com.beyond.ticketLink.notification.persistence.repository;


import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

//    private final NotificationRepository notificationRepository;
//
//    private final EventMapper eventMapper;
//
//    @Override
//    public List<Event> getList(EventSearchCond eventSearch) {
//        return eventMapper.getList(eventSearch);
//    }
//
//    @Override
//    public Optional<Event> getData(String eventNo) {
//        return eventMapper.getData(eventNo);
//    }
//
//    @Override
//    public Event insData(Event event) {
//        eventMapper.insData(event);
//
//        return event;
//    }
//
//    @Override
//    public void uptData(String eventNo, EventDto updateParam) {
//        eventMapper.uptData(eventNo, updateParam);
//    }




}
