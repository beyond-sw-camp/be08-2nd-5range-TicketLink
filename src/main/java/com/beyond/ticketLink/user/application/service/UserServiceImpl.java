package com.beyond.ticketLink.user.application.service;

import com.beyond.ticketLink.common.exception.TicketLinkException;
import com.beyond.ticketLink.smtp.exception.MailMessageType;
import com.beyond.ticketLink.smtp.persistence.entity.VerifiedEmail;
import com.beyond.ticketLink.smtp.persistence.repository.VerifiedEmailRepository;
import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import com.beyond.ticketLink.user.application.domain.UserRole;
import com.beyond.ticketLink.user.application.utils.JwtUtil;
import com.beyond.ticketLink.user.exception.UserMessageType;
import com.beyond.ticketLink.user.persistence.mariadb.dto.UserCreateDto;
import com.beyond.ticketLink.user.persistence.mariadb.repository.UserRepository;
import com.beyond.ticketLink.user.persistence.mariadb.repository.UserRoleRepository;
import com.beyond.ticketLink.user.persistence.redis.entity.ExpiredAccessToken;
import com.beyond.ticketLink.user.persistence.redis.repository.ExpiredAccessTokenRepository;
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

    private final ExpiredAccessTokenRepository expiredAccessTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(UserCreateRequest request) {

        final char ENABLE_USER = 'Y';
        final String ROLE_USER = "일반사용자";

        // 아이디가 중복 되었을 경우 409_Error throw
        if (userRepository.findUserById(request.id()).isPresent()) {
            throw new TicketLinkException(UserMessageType.DUPLICATE_USER_ID);
        }

        // db에 user role 설정이 잘못 되어 있을 경우 500_Error throw
        UserRole userRole = userRoleRepository.findByRoleName(ROLE_USER)
                .orElseThrow(() -> new TicketLinkException(UserMessageType.USER_ROLE_NOT_FOUND));

        // 이메일이 인증되지 않았을 경우 401_Error throw
        VerifiedEmail verifiedEmail = verifiedEmailRepository.findById(request.email())
                .orElseThrow(() -> new TicketLinkException(MailMessageType.EMAIL_UNAUTHORIZED));

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
                .orElseThrow(() -> new TicketLinkException(UserMessageType.USER_NOT_FOUND));

        // 비밀번호 검증
        if (invalidPassword(request, loginUser)) {
            throw new TicketLinkException(UserMessageType.INVALID_PASSWORD);
        }

        // JwtToken 생성
        String accessToken = jwtUtil.createAccessToken(loginUser);
        String refreshToken = jwtUtil.createRefreshToken(loginUser);

        return FindJwtResult.findByAll(accessToken, refreshToken);
    }

    @Override
    public FindUserResult getUserByUserNo(String userNo) {
        return FindUserResult.findByUser(
                userRepository.selectUserByUserNo(userNo)
                        .orElseThrow(() -> new TicketLinkException(UserMessageType.USER_NOT_FOUND))
        );
    }

    @Override
    @Transactional
    public void logout(LogoutCommand command) {
        String accessToken = command.getAccessToken();
        String userNo = command.getUserNo();

        // 현재 로그아웃 하는 accessToken 만료 토큰으로 등록
        expiredAccessTokenRepository.save(
                ExpiredAccessToken.builder()
                        .accessToken(accessToken)
                        // 8분으로 지정
                        .ttl(480L)
                        .build()
        );

    }

    @Override
    public void checkIdDuplicated(String id) {

        if (userRepository.findUserById(id).isPresent()) {
            throw new TicketLinkException(UserMessageType.DUPLICATE_USER_ID);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserById(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserMessageType.USER_NOT_FOUND.getMessage()));
    }

    private String encryptPassword(UserCreateRequest request) {
        return passwordEncoder.encode(request.pw());
    }

    private boolean invalidPassword(UserLoginRequest request, TicketLinkUserDetails loginUser) {
        return !passwordEncoder.matches(request.pw(), loginUser.getPassword());
    }
}
