package com.beyond.ticketLink.user.ui.controller;

import com.beyond.ticketLink.common.view.ApiResponseView;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.service.UserService;
import com.beyond.ticketLink.user.application.service.UserService.FindJwtResult;
import com.beyond.ticketLink.user.ui.requestbody.CheckDuplicateIdRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import com.beyond.ticketLink.user.ui.view.LoginView;
import com.beyond.ticketLink.user.ui.view.UserView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.beyond.ticketLink.user.application.service.UserService.*;
import static com.beyond.ticketLink.user.application.service.UserService.LogoutCommand;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping("/user/register")
    ResponseEntity<ApiResponseView<Void>> registerUser(@RequestBody @Validated UserCreateRequest request) {

        service.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/user/login")
    ResponseEntity<ApiResponseView<LoginView>> login(@RequestBody @Validated UserLoginRequest request) {
        FindJwtResult result = service.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new LoginView(result)));
    }

    @PostMapping("/user/logout")
    ResponseEntity<ApiResponseView<Void>> logout(HttpServletRequest request, @AuthenticationPrincipal String userNo) {
        log.info("userNo : {}", userNo);


        String authorization = request.getHeader("Authorization");

        String accessToken = authorization.substring(7);

        service.logout(
                LogoutCommand.builder()
                        .accessToken(accessToken)
                        .userNo(userNo)
                        .build()
        );

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/user/check-duplicate")
    ResponseEntity<ApiResponseView<Void>> checkDuplicate(@RequestBody @Validated CheckDuplicateIdRequest request) {

        service.checkIdDuplicated(request.id());

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/user/profile")
    ResponseEntity<ApiResponseView<UserView>> getUser(@AuthenticationPrincipal String userNo) {

        FindUserResult result = service.getUserByUserNo(userNo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new UserView(result)));
    }

    @GetMapping("/user/{userNo}")
    ResponseEntity<ApiResponseView<UserView>> getUserByAdmin(@PathVariable String userNo) {

        FindUserResult result = service.getUserByUserNo(userNo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseView<>(new UserView(result)));
    }
}
