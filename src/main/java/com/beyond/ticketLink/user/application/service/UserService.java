package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.application.domain.JwtToken;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    JwtToken login(UserLoginRequest request);

    void checkIdDuplicated(String id);
}
