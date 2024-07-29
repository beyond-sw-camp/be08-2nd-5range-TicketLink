package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.exception.BoardCategoryMessageType;
import com.beyond.ticketLink.board.persistence.repository.BoardCategoryRepository;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardCategoryServiceImpl implements BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    @Override
    public FindCategoryResult getCategoryByNo(BoardCategoryFindQuery query) {
        int bCategoryNo = query.getBCategoryNo();

        return FindCategoryResult.findByBoardCategory(
                boardCategoryRepository.findByNo(bCategoryNo)
                        .orElseThrow(() -> new TicketLinkException(BoardCategoryMessageType.BOARD_CATEGORY_NOT_FOUND))
        );
    }

    @Override
    public List<FindCategoryResult> selectAllCategory() {
        return boardCategoryRepository.findAll()
                .stream()
                .map(FindCategoryResult::findByBoardCategory)
                .toList();
    }

}
