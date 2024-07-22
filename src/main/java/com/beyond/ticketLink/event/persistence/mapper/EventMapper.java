package com.beyond.ticketLink.event.persistence.mapper;

import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.persistence.dto.EventDto;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {
    List<Event> getList(EventSearchCond eventSearch);

    Optional<Event> getData(String eventNo);

    void insData(Event event);

    void uptData(@Param("eventNo") String eventNo, @Param("updateParam") EventDto updateParam);
}
