package com.beyond.ticketLink.board.persistence.mapper;

import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    // 매퍼에 매개변수로 하나의 인자만 전달될 경우
    // @Param 생략 가능
    void save(BoardCreateDto board);
}
