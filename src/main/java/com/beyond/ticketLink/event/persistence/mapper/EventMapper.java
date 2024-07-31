package com.beyond.ticketLink.event.persistence.mapper;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.DayEventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import com.beyond.ticketLink.event.persistence.dto.EventStatsDto;
import com.beyond.ticketLink.event.persistence.dto.EventUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {
    List<Event> getList(EventSearchCond cond);

    Optional<Event> getData(@Param("eventNo") String eventNo, @Param("dto") DayEventSearchCond dto);

    List<EventStatsDto> getStatsList(EventSearchCond cond);

    void insData(Event event);

    void uptData(@Param("eventNo") String eventNo, @Param("updateParam") EventUpdateDto updateParam);
}
