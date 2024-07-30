package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.EventUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private EventServiceImpl eventService;

    @Test
    @Transactional
    void getList() {
        // given
        List<Event> list = eventService.getList(null);
        int size = list.size();

        // when
        Event event = new Event();
        event.setName("테스트");
        event.setStartDate(LocalDate.of(2024, 8, 1));
        event.setEndDate(LocalDate.of(2024, 8, 10));
        event.setLocation("테스트장소");
        eventService.insData(event);

        list = eventService.getList(null);

        //then
        assertNotNull(list);
        assertEquals(list.getLast().getName(), event.getName());
        assertEquals(list.size(), size + 1);
    }

    @Test
    void getData() {
        // given
        List<Event> list = eventService.getList(null);
        Event event = list.getFirst();

        // when
        Event searchEvent = eventService.getData(list.getFirst().getEventNo(), null).get();

        //then
        assertNotNull(searchEvent);
        assertEquals(searchEvent.getName(), event.getName());
        assertEquals(searchEvent.getStartDate(), event.getStartDate());
        assertEquals(searchEvent.getEndDate(), event.getEndDate());
        assertEquals(searchEvent.getLocation(), event.getLocation());
    }

    @Test
    @Transactional
    void insData() {
        // given
        Event event = new Event();
        event.setName("테스트");
        event.setStartDate(LocalDate.of(2024, 8, 1));
        event.setEndDate(LocalDate.of(2024, 8, 10));
        event.setLocation("테스트장소");
        eventService.insData(event);

        // when
        List<Event> list = eventService.getList(null);
        Event newEvent = eventService.getData(list.getLast().getEventNo(), null).get();

        //then
        assertNotNull(newEvent);
        assertEquals(newEvent.getName(), event.getName());
        assertEquals(newEvent.getStartDate(), event.getStartDate());
        assertEquals(newEvent.getEndDate(), event.getEndDate());
        assertEquals(newEvent.getLocation(), event.getLocation());
    }

    @Test
    @Transactional
    void uptData() {
        // given
        List<Event> list = eventService.getList(null);

        EventUpdateDto dto = new EventUpdateDto("테스트수정테스트", null, null, null, null, null, null);

        eventService.uptData(list.get(1).getEventNo(), dto);
        // when
        Event updateEvent = eventService.getData(list.get(1).getEventNo(), null).get();

        //then
        assertEquals(updateEvent.getName(), dto.getName());
    }
}