package com.beyond.ticketLink.autono.persistence.mapper;

import com.beyond.ticketLink.autono.application.domain.AutoNo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AutoNoMapper {
    Optional<AutoNo> getData(String tableName);
}
