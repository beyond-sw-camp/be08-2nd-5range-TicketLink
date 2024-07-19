package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.RefreshToken;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import com.beyond.ticketLink.user.persistence.dto.JwtCreateDto;
import com.beyond.ticketLink.user.persistence.dto.UserCreateDto;
import com.beyond.ticketLink.user.persistence.repository.JwtRepository;
import com.beyond.ticketLink.user.persistence.repository.UserRepository;
import com.beyond.ticketLink.user.persistence.repository.UserRoleRepository;
import com.beyond.ticketLink.user.ui.requestbody.UserCreateRequest;
import com.beyond.ticketLink.user.ui.requestbody.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final VerifiedEmailRepository verifiedEmailRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtRepository jwtRepository;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(UserCreateRequest request) {

        final char ENABLE_USER = 'Y';
        final String ROLE_USER = "일반사용자";

        // 아이디가 중복 되었을 경우 404_Error throw
        if (userRepository.findUserById(request.id()).isPresent()) {
            throw new TicketLinkException(MessageType.DUPLICATE_USER_ID);
        }

        // db에 user role 설정이 잘못 되어 있을 경우 500_Error throw
        UserRole userRole = userRoleRepository.findByRoleName(ROLE_USER)
                .orElseThrow(() -> new TicketLinkException(MessageType.USER_ROLE_NOT_FOUND));

        // 이메일이 인증되지 않았을 경우 401_Error throw
        VerifiedEmail verifiedEmail = verifiedEmailRepository.findById(request.email())
                .orElseThrow(() -> new TicketLinkException(MessageType.EMAIL_UNAUTHORIZED));

        // 패스워드 암호화
        String encryptedPassword = encryptPassword(request);

        // 유저 저장
        userRepository.save(
                new UserCreateDto(
                        request.id(),
                        encryptedPassword,
                        request.name(),
                        request.email(),
                        ENABLE_USER,
                        userRole.getId()
                )
        );

        // 유저 저장 이후 이메일 인증 완료 정보 삭제
        verifiedEmailRepository.delete(verifiedEmail);
    }

    @Override
    public FindJwtResult login(UserLoginRequest request) {

        // 로그인 요청한 유저가 존재하지 않을 경우 404_Error throw
        TicketLinkUserDetails loginUser = userRepository.findUserById(request.id())
                .orElseThrow(() -> new TicketLinkException(MessageType.USER_NOT_FOUND));

        // 비밀번호 검증
        if (invalidPassword(request, loginUser)) {
            throw new TicketLinkException(MessageType.INVALID_PASSWORD);
        }

        // JwtToken 생성
        String accessToken = jwtUtil.createAccessToken(loginUser);
        String refreshToken = jwtUtil.createRefreshToken(loginUser);

        // JwtToken 저장
        jwtRepository.save(
                new JwtCreateDto(accessToken,refreshToken, loginUser.getUserNo())
        );

        return FindJwtResult.findByAll(accessToken, refreshToken);
    }


    @Override
    public void checkIdDuplicated(String id) {

        if (userRepository.findUserById(id).isPresent()) {
            throw new TicketLinkException(MessageType.DUPLICATE_USER_ID);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserById(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageType.USER_NOT_FOUND.getMessage()));
    }

    private String encryptPassword(UserCreateRequest request) {
        return passwordEncoder.encode(request.pw());
    }

    private boolean invalidPassword(UserLoginRequest request, TicketLinkUserDetails loginUser) {
        return !passwordEncoder.matches(request.pw(), loginUser.getPassword());
    }
}
