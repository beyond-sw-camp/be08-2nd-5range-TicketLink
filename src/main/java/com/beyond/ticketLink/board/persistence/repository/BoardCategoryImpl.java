package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.BoardCategory;
import com.beyond.ticketLink.board.persistence.mapper.BoardCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardCategoryImpl implements BoardCategoryRepository {

    private final BoardCategoryMapper boardCategoryMapper;

    @Override
    public Optional<BoardCategory> findByNo(int no) {
        return boardCategoryMapper.findByNo(no);
    }

    @Override
    public List<BoardCategory> findAll() {
        return boardCategoryMapper.findAll();
    }
}
