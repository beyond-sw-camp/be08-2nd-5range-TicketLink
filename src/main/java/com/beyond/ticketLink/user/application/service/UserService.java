package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserCreateRequest request);

    void throwErrorWithDuplicateId(String id);
}
