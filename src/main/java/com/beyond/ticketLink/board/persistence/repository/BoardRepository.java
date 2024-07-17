package com.beyond.ticketLink.board.persistence.repository;

import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;

public interface BoardRepository {
    void save(BoardCreateDto board);
}
