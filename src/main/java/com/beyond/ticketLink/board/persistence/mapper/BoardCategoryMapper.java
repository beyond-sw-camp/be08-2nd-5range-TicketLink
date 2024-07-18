package com.beyond.ticketLink.board.persistence.mapper;

import com.beyond.ticketLink.board.application.domain.BoardCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardCategoryMapper {
    Optional<BoardCategory> findByNo(int no);

    List<BoardCategory> findAll();
}
