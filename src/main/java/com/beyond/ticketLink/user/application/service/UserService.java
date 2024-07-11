package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.application.domain.JwtToken;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    JwtToken login(UserLoginRequest request);

    void checkIdDuplicated(String id);
}
