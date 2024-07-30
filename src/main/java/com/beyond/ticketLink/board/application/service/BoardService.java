package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.service.BoardCategoryService.FindBoardCategoryResult;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.reply.application.service.ReplyService.FindReplyResult;
import com.beyond.ticketLink.user.application.service.UserService.FindUserResult;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BoardService {

    // 게시판 작성
    void createBoard(BoardCreateRequest request, String userNo);

    List<FindBoardResult> selectAllBoard(BoardFindQuery query);

    FindBoardResult getBoardByNo(BoardFindQuery query);

    FindBoardResult modifyBoard(BoardUpdateCommand command);

    void deleteBoard(BoardDeleteCommand command);


    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class FindBoardResult {
        private final String boardNo;
        private final String title;
        private final String content;
        private final Float rating;
        private final Date insDate;
        private final Date uptDate;

        // board category
        private final Integer boardCategoryNo;
        private final String boardCategoryName;

        // board writer
        private final String username;

        private final List<FindReplyResult> replies;


        public static FindBoardResult findByBoard(Board board) {
            FindBoardResultBuilder builder = initDefault(board);

            Optional.ofNullable(board.getUser())
                    .map(FindUserResult::findByUser)
                    .ifPresent(user -> {
                        builder.username(user.getName());
                    });

            Optional.ofNullable(board.getCategory())
                    .map(FindBoardCategoryResult::findByBoardCategory)
                    .ifPresent(boardCategory -> {
                        builder.boardCategoryNo(boardCategory.getBCategoryNo())
                                .boardCategoryName(boardCategory.getName());
                    });

            Optional.ofNullable(board.getReplies())
                    .map(replies -> replies.stream()
                            .map(FindReplyResult::findByReply)
                            .toList())
                    .ifPresent(builder::replies);

            return builder.build();
        }

        private static FindBoardResultBuilder initDefault(Board board) {
            return FindBoardResult.builder()
                    .boardNo(board.getBoardNo())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .rating(board.getRating())
                    .insDate(board.getInsDate())
                    .uptDate(board.getUptDate());
        }

        public static FindBoardResult findByBoardUpdateDto(BoardUpdateDto boardUpdateDto) {
            return FindBoardResult.builder()
                    .boardNo(boardUpdateDto.getBoardNo())
                    .title(boardUpdateDto.getTitle())
                    .content(boardUpdateDto.getContent())
                    .uptDate(boardUpdateDto.getUptDate())
                    .rating(boardUpdateDto.getRating())
                    .build();
        }


    }

    @Getter
    @Builder
    @ToString
    class BoardUpdateCommand {
        private final String boardNo;
        private final String userNo;
        private final String title;
        private final String content;
        private final Float rating;
    }

    @Getter
    @Builder
    @ToString
    class BoardDeleteCommand {
        private final String boardNo;
        private final String userNo;
    }

//    // 게시판 작성에 필요한 데이터를 담는 VO 객체
//    @Getter
//    @Builder
//    @ToString
//    class BoardCreateCommand {
//        private final String title;
//        private final String content;
//        private final String
//    }

}
