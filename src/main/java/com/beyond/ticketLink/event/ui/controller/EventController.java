package com.beyond.ticketLink.event.ui.controller;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.service.EventService;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket/event")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getList(@RequestBody EventSearchCond cond) {
        List<Event> list = eventService.getList(cond);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{eventNo}")
    public ResponseEntity<Event> getData(@PathVariable String eventNo) {
        Event event = eventService.getData(eventNo).get();

        return ResponseEntity.ok(event);
    }

    @PostMapping("/register")
    public ResponseEntity<Event> register(@RequestBody Event event) {
        Event newEvent = eventService.insData(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @PutMapping("/{eventNo}/update")
    public ResponseEntity<Event> update(@PathVariable String eventNo, @RequestBody EventDto dto) {
        eventService.uptData(eventNo, dto);

        return ResponseEntity.ok(eventService.getData(eventNo).get());
    }
}
