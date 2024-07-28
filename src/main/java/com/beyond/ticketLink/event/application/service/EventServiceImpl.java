package com.beyond.ticketLink.event.application.service;

import com.beyond.ticketLink.autono.persistence.repository.AutoNoRepository;
import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.persistence.dto.DayEventDto;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.repository.DayEventRepository;
import com.beyond.ticketLink.event.persistence.repository.EventRepository;
import com.beyond.ticketLink.event.persistence.repository.TicketRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final AutoNoRepository autoNoRepository;
    private final EventRepository eventRepository;
    private final DayEventRepository dayEventRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<Event> getList(EventSearchCond eventSearch) {
        return eventRepository.getList(eventSearch);
    }

    @Override
    public Optional<Event> getData(String eventNo, DayEventDto dto) {
        if (dto != null) {
            if (dto.getDayInfo() != null) {
                dto.setDays(Arrays.asList(dto.getDayInfo().split(",")));
            }
            if (dto.getTimeInfo() != null) {
                dto.setTimes(Arrays.asList(dto.getTimeInfo().split(",")));
            }
            if (dto.getCastInfo() != null) {
                dto.setCasts(Arrays.asList(dto.getCastInfo().split(",")));
            }
        }

        return eventRepository.getData(eventNo, dto);
    }

    @Override
    @Transactional
    public Event insData(Event event) {
        String eventNo = autoNoRepository.getData("tb_event");
        DailyEvent dayEvent;
        String dayEventNo;

        // event 데이터 생성
        event.setEventNo(eventNo);

        eventRepository.insData(event);

        // 회차 정보와 좌석정보가 존재하는 행사
        if (event.getSeatInfo() != null && event.getTimeInfo() != null) {

            // dayEvent
            LocalDate startDate = event.getStartDate();
            LocalDate endDate = event.getEndDate();
            int sDay = startDate.getDayOfWeek().getValue(); // 시작 날짜 요일(1(월) ~ 7(일))
            long dayDiff = ChronoUnit.DAYS.between(startDate, endDate);// 시작일자와 종료일자 차이
            StringTokenizer timeInfoSt = new StringTokenizer(event.getTimeInfo(), "@");
            ArrayList<TimeInfo> timeInfoAl = new ArrayList<>();

            while (timeInfoSt.hasMoreTokens()) {
                TimeInfo timeInfo = new TimeInfo();

                StringTokenizer timeSt = new StringTokenizer(timeInfoSt.nextToken(), "-");
                timeInfo.setDay(Integer.parseInt(timeSt.nextToken()));
                timeInfo.setCnt(Integer.parseInt(timeSt.nextToken()));
                timeInfo.setTime(timeSt.nextToken());

                timeInfoAl.add(timeInfo);
            }

            // ticket
            Ticket ticket;
            String ticketNo;
            StringTokenizer seatInfoSt = new StringTokenizer(event.getSeatInfo(), "@");
            int seatCount = Integer.parseInt(seatInfoSt.nextToken());
            ArrayList<Map<String, Object>> al = new ArrayList<>();
            Map<String, Object> map;

            while (seatInfoSt.hasMoreTokens()) {
                map = new HashMap<>();
                StringTokenizer seatRateSt = new StringTokenizer(seatInfoSt.nextToken(), ":");
                map.put("seatRate", seatRateSt.nextToken());
                map.put("seatRateCnt", Integer.parseInt(seatRateSt.nextToken()));
                map.put("seatRatePrice", Integer.parseInt(seatRateSt.nextToken()));
                al.add(map);
            }

            int curSeatCnt = 0;
            String seatRate = null;
            int seatRateCnt = 0;
            int seatRatePrice = 0;

            //log.info("startDate: {}, endDate {}", event.getStartDate(), event.getEndDate());
            //log.info("차이일수: {}, 시작일짜 요일 : {}", dayDiff, sDay);
            //log.info("총 티켓 수 : {}", seatCount);

            int sIdx, eIdx;

            // 일자별 행사, 티켓 동시 생성
            for (int i = 0; i <= dayDiff; i++) {
                // 행사 일정이 아니면 pass
                if (!timeInfoAl.contains(new TimeInfo(sDay, 0, null))) {
                    //log.info("날짜 {} - 요일 {} - pass", startDate, sDay);
                    startDate = startDate.plusDays(1);
                    sDay = sDay < 7 ? sDay + 1 : 1;
                    continue;
                } else {
                    sIdx = timeInfoAl.indexOf(new TimeInfo(sDay, 0, null));
                    eIdx = timeInfoAl.lastIndexOf(new TimeInfo(sDay, 0, null));

                    //log.info("날짜 {} - 요일 {} - 작동 - 횟수 : {}", startDate, sDay, (eIdx - sIdx));

                    for (int j = sIdx; j <= eIdx; j++) {
                        dayEvent = new DailyEvent();
                        dayEventNo = autoNoRepository.getData("tb_dailyEvent");

                        dayEvent.setDayEventNo(dayEventNo);
                        dayEvent.setEventDate(startDate);
                        dayEvent.setDay7(sDay);
                        dayEvent.setCnt(timeInfoAl.get(j).getCnt());
                        dayEvent.setDeTime(timeInfoAl.get(j).getTime());
                        dayEvent.setEventNo(eventNo);

                        // dayEvent ins
                        dayEventRepository.insData(dayEvent);
                        int seatIdx = 0;

                        for (int k = 0; k < seatCount; k++) {
                            ticket = new Ticket();
                            ticketNo = autoNoRepository.getData("tb_ticket");

                            if (curSeatCnt == 0) {
                                seatRate = al.get(seatIdx).get("seatRate").toString();
                                seatRateCnt = Integer.parseInt(al.get(seatIdx).get("seatRateCnt").toString());
                                seatRatePrice = Integer.parseInt(al.get(seatIdx).get("seatRatePrice").toString());
                                seatIdx++;
                                //log.info("등급 : {}, 좌석수:  {}, 가격 : {}", seatRate, seatRateCnt, seatRatePrice);
                            }

                            ticket.setTicketNo(ticketNo);
                            ticket.setSeatRate(seatRate);
                            ticket.setSeatNum(k);
                            ticket.setPrice(seatRatePrice);
                            ticket.setDayEventNo(dayEventNo);

                            // ticket ins
                            ticketRepository.insData(ticket);
                            curSeatCnt++;

                            if (curSeatCnt == seatRateCnt) {
                                curSeatCnt = 0;
                            }
                        }
                    }
                }

                startDate = startDate.plusDays(1);
                sDay = sDay < 7 ? sDay + 1 : 1;
            }
            // 상시상품(날짜와 좌석이 상관 없는) 행사인 경우 티켓정보 생성x, 결제단계에서 티켓정보 생성
        } else {
            dayEvent = new DailyEvent();
            dayEventNo = autoNoRepository.getData("tb_dailyEvent");
            dayEvent.setDayEventNo(dayEventNo);
            dayEvent.setEventNo(eventNo);
            // dayEvent ins
            dayEventRepository.insData(dayEvent);
        }

        return event;
    }

    @Override
    @Transactional
    public void uptData(String eventNo, EventDto updateParam) {
        eventRepository.uptData(eventNo, updateParam);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    static class TimeInfo {
        int day;
        int cnt;
        String time;

        @Override
        public boolean equals(Object obj) {
            TimeInfo timeInfo = (TimeInfo) obj;

            return timeInfo.day == day;
        }
    }
}