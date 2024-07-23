package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    FindJwtResult login(UserLoginRequest request);

    void checkIdDuplicated(String id);

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class FindJwtResult {
        private final String accessToken;
        private final String refreshToken;

        static FindJwtResult findByAll(String accessToken, String refreshToken) {
            return FindJwtResult.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
