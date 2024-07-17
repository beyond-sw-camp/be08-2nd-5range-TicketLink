package com.beyond.ticketLink.board.ui.controller;

import com.beyond.ticketLink.board.application.domain.Board;
import com.beyond.ticketLink.board.application.service.BoardService;
import com.beyond.ticketLink.board.ui.view.BoardView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // ResponseEntity
    /**
     * ResponseEntity 객체는 HTTP 응답 전체를 표현할 수 있는 객체이다.
     * 응답 상태 코드, 헤더, 응답 본문 등을 설정할 수 있다.
     * 이 컨트롤러 메서드는 게시글을 추가하고, 결과를 ResponseEntity로 감싸서 반환한다.
     *
     * 만약 response할 데이터가 없을 경우 Void 타입을 명시하면 된다.
     * ex) ResponseEntity<Void>
     */

    @PostMapping("/board")
    public ResponseEntity<BoardView> addBoard() {
        // 실제 서비스 로직을 호출하여 BoardView 객체를 생성하는 부분이 필요
        // 예: BoardView boardView = boardService.createBoard(...);

        // 현재는 단순히 빈 BoardView 객체를 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BoardView(new Board()));
    }

}