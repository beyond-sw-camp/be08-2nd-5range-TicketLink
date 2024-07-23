package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.persistence.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public void save(BoardCreateDto board) {
        boardMapper.save(board);
    }

    @Override
    public Optional<Board> selectBoardByBoardNo(String boardNo) {
        return boardMapper.selectBoardByBoardNo(boardNo);
    }

    @Override
    public List<Board> selectBoardAll(BoardFindQuery query) {
        return boardMapper.selectBoardAll(query);
    }

    @Override
    public void updateBoard(BoardUpdateDto board) {
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(String boardNo) {
        boardMapper.deleteBoard(boardNo);
    }
}
