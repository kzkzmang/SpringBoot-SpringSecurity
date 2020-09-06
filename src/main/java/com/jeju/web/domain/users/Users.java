package com.jeju.web.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jeju.web.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String hasAuthority;

    @Column(length = 1, nullable = true)
    private Boolean loginCheck;

    public void setLoginCheck(Boolean loginCheck) {
        this.loginCheck = loginCheck;
    }

    @Builder
    public Users(String username, String password, String hasAuthority, Boolean loginCheck) {
        this.username = username;
        this.password = password;
        this.hasAuthority = hasAuthority;
        this.loginCheck = loginCheck;
    }
}
