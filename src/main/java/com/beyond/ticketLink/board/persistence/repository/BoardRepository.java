package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    void save(BoardCreateDto board);

    Optional<Board> selectBoardByBoardNo(@Param("boardNo") String boardNo);

    List<Board> selectBoardAll(BoardFindQuery query);

    void updateBoard(BoardUpdateDto board);

    void deleteBoard(String boardNo);
}
