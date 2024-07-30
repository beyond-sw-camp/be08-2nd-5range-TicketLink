package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    FindJwtResult login(UserLoginRequest request);

    FindUserResult getUserByUserNo(String userNo);

    void logout(LogoutCommand command);

    void checkIdDuplicated(String id);

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    class FindUserResult {
        private final String userNo;
        private final String id;
        private final String name;
        private final String email;
        private final char useYn;
        private final String role;

        public static FindUserResult findByUser(TicketLinkUserDetails user) {

            FindUserResultBuilder builder = initDefault(user);

            Optional.ofNullable(user.getRole())
                    .ifPresent(role -> {
                        builder.role(role.getName());
                    });

            return builder.build();
        }

        private static FindUserResultBuilder initDefault(TicketLinkUserDetails user) {
            return FindUserResult.builder()
                    .userNo(user.getUserNo())
                    .id(user.getId())
                    .name(user.getUsername())
                    .email(user.getEmail())
                    .useYn(user.getUseYn());
        }
    }

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
