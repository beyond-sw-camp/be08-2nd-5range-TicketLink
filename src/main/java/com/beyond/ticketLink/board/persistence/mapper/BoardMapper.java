package com.beyond.ticketLink.board.persistence.mapper;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 매퍼에 매개변수로 하나의 인자만 전달될 경우
    // @Param 생략 가능
    void save(BoardCreateDto board);

    Optional<Board> selectBoardByBoardNo(@Param("boardNo") String boardNo);

    List<Board> selectBoardAll(@Param("query") BoardFindQuery query, RowBounds rowBounds);

    void updateBoard(BoardUpdateDto board);

    void deleteBoard(String boardNo);
}
