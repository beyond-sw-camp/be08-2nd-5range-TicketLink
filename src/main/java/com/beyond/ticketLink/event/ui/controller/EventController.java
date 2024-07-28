package com.beyond.ticketLink.event.ui.controller;

import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.service.DayEventService;
import com.beyond.ticketLink.event.application.service.EventService;
import com.beyond.ticketLink.event.application.service.TicketService;
import com.beyond.ticketLink.event.persistence.dto.DayEventDto;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.TicketCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EventController {
    private final EventService eventService;
    private final DayEventService dayEventService;
    private final TicketService ticketService;

    // 이벤트 전체 리스트(검색조건 : 카테고리, 이름)
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getList(@ModelAttribute EventSearchCond cond) {
        List<Event> list = eventService.getList(cond);

        return ResponseEntity.ok(list);
    }

    // 이벤트 상세 정보
    @GetMapping("/events/{eventNo}")
    public ResponseEntity<Event> getData(@PathVariable String eventNo, @ModelAttribute DayEventDto dto) {
        Event event = eventService.getData(eventNo, dto).get();

        return ResponseEntity.ok(event);
    }

    // 일자별 이벤트 리스트 조회
    @GetMapping("/events/{eventNo}/{eventDate}")
    public ResponseEntity<List<DailyEvent>> getDataByDate(@PathVariable String eventNo
            , @PathVariable String eventDate) {
        List<DailyEvent> list = dayEventService.getList(eventNo, eventDate);

        return ResponseEntity.ok(list);
    }

    // 이벤트 잔여 티켓(좌석) 조회
    @GetMapping("/events/{eventNo}/{eventDate}/{dayEventNo}")
    public ResponseEntity<List<TicketCount>> getDataByDate(@PathVariable String eventNo
            , @PathVariable String eventDate, @PathVariable String dayEventNo) {
        List<TicketCount> list = ticketService.getCounts(dayEventNo);

        return ResponseEntity.ok(list);
    }

    // 이벤트 등록
    @PostMapping("/event/register")
    public ResponseEntity<Event> register(@RequestBody Event event) {
        //log.info("event: {}", event);

        Event newEvent = eventService.insData(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    // 이벤트 수정
    @PutMapping("/event/{eventNo}")
    public ResponseEntity<Event> update(@PathVariable String eventNo, @RequestBody EventDto dto) {
        //log.info("dto : {}", dto);
        eventService.uptData(eventNo, dto);

        return ResponseEntity.ok(eventService.getData(eventNo, null).get());
    }
}
