package com.beyond.ticketLink.event.ui.controller;

import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.service.DayEventService;
import com.beyond.ticketLink.event.application.service.EventService;
import com.beyond.ticketLink.event.application.service.TicketService;
import com.beyond.ticketLink.event.persistence.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Event APIs", description = "행사 관련 API 목록")
public class EventController {
    private final EventService eventService;
    private final DayEventService dayEventService;
    private final TicketService ticketService;

    @GetMapping("/events")
    @Operation(summary = "행사 목록 조회", description = "전체 행사의 목록을 조회한다.(비회원도 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Event.class))
                    )
            }
    )
    public ResponseEntity<List<Event>> getList(@ModelAttribute EventSearchCond cond) {
        List<Event> list = eventService.getList(cond);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/events/{eventNo}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Event.class))
                    )
            }
    )
    @Operation(summary = "행사 상세 조회", description = "선택한 행사의 상세 정보를 조회한다.(비회원도 가능)")
    public ResponseEntity<Event> getData(@PathVariable String eventNo, @ModelAttribute DayEventSearchCond dto) {
        Event event = eventService.getData(eventNo, dto).get();

        return ResponseEntity.ok(event);
    }

    @GetMapping("/events/{eventNo}/{eventDate}")
    @Operation(summary = "일자별 행사 목록 조회", description = "선택한 행사의 일자별 목록을 조회한다.(비회원도 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = DailyEvent.class))
                    )
            }
    )
    public ResponseEntity<List<DailyEvent>> getDataByDate(@PathVariable String eventNo
            , @PathVariable String eventDate) {
        List<DailyEvent> list = dayEventService.getList(eventNo, eventDate);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/events/{eventNo}/{eventDate}/{dayEventNo}")
    @Operation(summary = "행사 잔여 티켓(좌석) 조회", description = "선택한 행사의 잔여 티켓(좌석)을 조회한다.(비회원도 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TicketCount.class))
                    )
            }
    )
    public ResponseEntity<List<TicketCount>> getDataByDate(@PathVariable String eventNo
            , @PathVariable String eventDate, @PathVariable String dayEventNo) {
        List<TicketCount> list = ticketService.getCounts(dayEventNo);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/event/stats")
    @Operation(summary = "행사 예매 현황 조회", description = "행사 예매 현황을 조회한다.(관리자만 가능)\n상시행사의 경우 전체 티켓을 0으로 표기")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EventStatsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "관리자가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<List<EventStatsDto>> getStats(@ModelAttribute EventSearchCond cond) {
        List<EventStatsDto> list = eventService.getStats(cond);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/event/register")
    @Operation(summary = "행사 정보 생성", description = "행사 정보를 생성한다.(관리자만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = BoardCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "관리자가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<Event> register(@RequestBody Event event) {
        //log.info("event: {}", event);

        Event newEvent = eventService.insData(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @PutMapping("/event/{eventNo}")
    @Operation(summary = "행사 정보 수정", description = "행사의 정보를 수정한다.(관리자만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Event.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "관리자가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "수정하고자 하는 행사가 존재하지 않는 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<Event> update(@PathVariable String eventNo, @RequestBody EventUpdateDto dto) {
        //log.info("dto : {}", dto);
        eventService.uptData(eventNo, dto);

        return ResponseEntity.ok(eventService.getData(eventNo, null).get());
    }
}
