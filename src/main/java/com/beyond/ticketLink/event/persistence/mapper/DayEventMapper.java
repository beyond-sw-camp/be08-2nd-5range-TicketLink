package com.beyond.ticketLink.event.persistence.mapper;

import com.beyond.ticketLink.event.application.domain.DailyEvent;
import com.beyond.ticketLink.event.persistence.dto.EventSearchCond;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DayEventMapper {
    List<DailyEvent> getList(EventSearchCond eventSearch);

    Optional<DailyEvent> getData(String dayEventNo);

    void insData(DailyEvent dayEvent);
}
