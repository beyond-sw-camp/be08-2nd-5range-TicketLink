package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    FindJwtResult login(UserLoginRequest request);

    void logout(LogoutCommand command);

    void checkIdDuplicated(String id);


    @Getter
    @Builder
    @EqualsAndHashCode
    class FindJwtResult {
        private final String accessToken;
        private final String refreshToken;

        public static FindJwtResult findByAll(String accessToken, String refreshToken) {
            return FindJwtResult.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @Getter
    @Builder
    class LogoutCommand {
        private final String accessToken;
        private final String userNo;
    }
}
