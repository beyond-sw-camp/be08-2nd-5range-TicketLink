package com.beyond.ticketLink.user.application.utils;

import com.beyond.ticketLink.user.application.domain.TicketLinkUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void getUserNo_shouldGetUserNoSuccessfully() {
        // given
        TicketLinkUserDetails member = new TicketLinkUserDetails("test",
                "testUserA",
                "1234",
                "testUserA",
                "testUserA@beyond.com",
                'Y',
                "일반사용자"
        );
        String accessToken = jwtUtil.createAccessToken(member);
        // when
        String userNo = jwtUtil.getUserNo(accessToken);
        // then
        assertThat(userNo).isEqualTo(member.getUserNo());
    }

    @Test
    public void getRole_shouldGetRoleSuccessfully() {
        // given
        TicketLinkUserDetails member = new TicketLinkUserDetails("test",
                "testUserA",
                "1234",
                "testUserA",
                "testUserA@beyond.com",
                'Y',
                "일반사용자"
        );
        String accessToken = jwtUtil.createAccessToken(member);
        // when
        String role = jwtUtil.getRole(accessToken);
        // then
        assertThat(role).isEqualTo(member.getRole());
    }

}