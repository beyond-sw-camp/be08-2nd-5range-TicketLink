package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.application.domain.BoardCategory;

import java.util.List;
import java.util.Optional;

public interface BoardCategoryRepository {
    Optional<BoardCategory> findByNo(int no);

    List<BoardCategory> findAll();
}
