package com.jeju.web.domain.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jeju.web.domain.BaseTimeEntity;
import com.jeju.web.domain.userMenus.UserMenus;

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
    private Long userId;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 512, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String hasAuthority;

    @Column(length = 1, nullable = true)
    private Boolean loginCheck;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
    private List<UserMenus> userMenus = new ArrayList<>();

    public void addMenus(UserMenus userMenu) {
        userMenus.add(userMenu);
        userMenu.setUsers(this);
    }

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
