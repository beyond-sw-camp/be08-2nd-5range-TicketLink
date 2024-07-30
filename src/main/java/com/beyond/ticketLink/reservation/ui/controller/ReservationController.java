package com.beyond.ticketLink.reservation.ui.controller;

import com.beyond.ticketLink.board.persistence.dto.BoardCreateDto;
import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.event.application.domain.Event;
import com.beyond.ticketLink.event.application.domain.Ticket;
import com.beyond.ticketLink.event.application.service.TicketService;
import com.beyond.ticketLink.reservation.application.domain.PayInfo;
import com.beyond.ticketLink.reservation.application.service.PayService;
import com.beyond.ticketLink.reservation.persistence.dto.PayDto;
import com.beyond.ticketLink.reservation.persistence.dto.PayListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Reservation APIs", description = "결제&예약 관련 API 목록")
public class ReservationController {
    private final TicketService ticketService;
    private final PayService payService;

    @GetMapping("/res/{dayEventNo}")
    @Operation(summary = "선택한 행사의 티켓(좌석) 현황 조회", description = "선택한 행사의 티켓(좌석) 현황 조회한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Ticket.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable("dayEventNo") String dayEventNo) {
        List<Ticket> tickets = ticketService.getList(dayEventNo);

        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @PostMapping("/res/{dayEventNo}")
    @Operation(summary = "행사 결제 및 예약 정보 생성", description = "선택한 행사에 대한 결제 및 예약 정보를 생성한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = BoardCreateDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 예약된 좌석일 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<PayInfo> register(@PathVariable String dayEventNo, @RequestBody PayDto payDto, @AuthenticationPrincipal String userNo) {
        PayInfo newPayInfo = payService.insData(dayEventNo, payDto, userNo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPayInfo);
    }

    @GetMapping("/res/myInfo")
    @Operation(summary = "나의 예약 정보 조회", description = "나의 예약 정보를 조회한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PayListDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<List<PayListDto>> getPayList(@AuthenticationPrincipal String userNo) {
        return ResponseEntity.status(HttpStatus.OK).body(payService.getList(userNo));
    }

    // 결제(예약) 정보 상세 조회
    @GetMapping("/res/myInfo/{payNo}")
    @Operation(summary = "나의 예약 정보 상세 조회", description = "나의 예약 정보를 상세 조회한다.(회원만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PayInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 상태가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    )
            }
    )
    public ResponseEntity<PayInfo> getPayInfo(@PathVariable String payNo) {
        return ResponseEntity.status(HttpStatus.OK).body(payService.getData(payNo).get());
    }

    // 결제(예약) 취소
    @PutMapping("/res/myInfo/{payNo}")
    @Operation(summary = "예약 취소", description = "선택한 예약을 취소한다.(회원, 관리자만 가능)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Event.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "request body 형식이 옳바르지 않을 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "본인의 결제 정보가 아닌 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "수정하고자 하는 결제가 존재하지 않는 경우",
                            content = @Content(schema = @Schema(implementation = ApiErrorView.class))
                    ),
            }
    )
    public ResponseEntity<PayInfo> update(@PathVariable String payNo) {
        return ResponseEntity.status(HttpStatus.OK).body(payService.updateData(payNo));
    }
}
