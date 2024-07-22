package com.beyond.ticketLink.autono.persistence.repository;

import com.beyond.ticketLink.autono.persistence.mapper.AutoNoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AutoNoRepositoryImpl implements AutoNoRepository{
    private final AutoNoMapper autoNoMapper;

    @Override
    public String getData(String tableName) {
        return autoNoMapper.getData(tableName).get().getAutoNo();
    }
}
