package com.beyond.ticketLink.user.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketLinkUserDetails implements UserDetails {

    private String userNo;
    private String id;
    private String pw;
    private String name;
    private String email;
    private char useYn;
    private UserRole role;

    @Override
    public String getPassword() {
        return this.pw;
    }

    // 아이디 반환
    @Override
    public String getUsername() {
        return this.id;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return useYn == 'Y';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
    }
}
