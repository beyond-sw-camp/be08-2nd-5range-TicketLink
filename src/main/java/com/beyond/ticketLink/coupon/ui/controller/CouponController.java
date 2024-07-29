package com.beyond.ticketLink.coupon.ui.controller;

import com.beyond.ticketLink.common.view.ApiErrorView;
import com.beyond.ticketLink.coupon.application.domain.Coupon;
import com.beyond.ticketLink.coupon.application.service.CouponService;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponCreateRequest;
import com.beyond.ticketLink.coupon.ui.requestbody.CouponUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
@Tag(name = "Coupon API", description = "API for managing coupons")
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 생성
    @Operation(summary = "쿠폰 생성", description = "쿠폰 생성 API - 로그인한 사용자만 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = CouponCreateRequest.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ApiErrorView.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody CouponCreateRequest request) {
        couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 쿠폰 번호로 쿠폰 조회
    @Operation(summary = "쿠폰 조회", description = "쿠폰 번호로 쿠폰 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Coupon.class))),
            @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ApiErrorView.class)))
    })
    @GetMapping("/{couponNo}")
    public ResponseEntity<Coupon> getCouponByNo(@PathVariable String couponNo) {
        return couponService.getCouponByNo(couponNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 사용자 쿠폰 목록 조회
    @Operation(summary = "사용자 쿠폰 목록 조회", description = "사용자 쿠폰 목록 조회 API - 로그인한 사용자만 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Coupon.class))),
            @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ApiErrorView.class)))
    })
    @GetMapping
    public ResponseEntity<List<Coupon>> getCouponsByUserNo(
            @RequestParam String userNo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Coupon> coupons = couponService.getCouponsByUserNo(userNo, page, size);
        return ResponseEntity.ok(coupons);
    }

    // 쿠폰 업데이트
    @Operation(summary = "쿠폰 업데이트", description = "쿠폰 업데이트 API - 로그인한 사용자만 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Coupon.class))),
            @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않은 경우", content = @Content(schema = @Schema(implementation = ApiErrorView.class))),
            @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ApiErrorView.class)))
    })
    @PutMapping("/{couponNo}")
    public ResponseEntity<Void> updateCoupon(@PathVariable String couponNo, @RequestBody CouponUpdateRequest request) {
        couponService.updateCoupon(couponNo, request);
        return ResponseEntity.ok().build();
    }

    // 쿠폰 삭제
    @Operation(summary = "쿠폰 삭제", description = "쿠폰 삭제 API - 로그인한 사용자만 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없는 경우", content = @Content(schema = @Schema(implementation = ApiErrorView.class)))
    })
    @DeleteMapping("/{couponNo}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable String couponNo) {
        couponService.deleteCoupon(couponNo);
        return ResponseEntity.ok().build();
    }
}
