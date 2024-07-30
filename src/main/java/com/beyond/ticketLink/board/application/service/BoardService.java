package com.beyond.ticketLink.board.application.service;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.domain.BoardCategory;
import com.beyond.ticketLink.board.persistence.dto.BoardFindQuery;
import com.beyond.ticketLink.board.persistence.dto.BoardUpdateDto;
import com.beyond.ticketLink.board.ui.requestbody.BoardCreateRequest;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.reply.application.domain.Reply;
import com.beyond.ticketLink.reply.application.service.ReplyService;
import com.beyond.ticketLink.reply.application.service.ReplyService.FindReplyResult;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.application.service.UserService.FindUserResult;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

import static com.beyond.ticketLink.user.application.service.UserService.FindUserResult.*;

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
        private final FindUserResult user;
        private final Event event;
        private final BoardCategory category;
        private final List<FindReplyResult> replies;

        public static FindBoardResult findByBoard(Board board) {
            return FindBoardResult.builder()
                    .boardNo(board.getBoardNo())
                    .title(board.getTitle())
                    .content(board.getTitle())
                    .rating(board.getRating())
                    .insDate(board.getInsDate())
                    .uptDate(board.getUptDate())
                    .user(findByUser(board.getUser()))
                    .event(board.getEvent())
                    .category(board.getCategory())
                    .replies(
                            board.getReplies()
                            .stream()
                            .map(FindReplyResult::findByReply)
                            .toList()
                    )
                    .build();
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
