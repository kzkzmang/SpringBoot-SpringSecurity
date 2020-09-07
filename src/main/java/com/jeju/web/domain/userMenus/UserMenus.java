package com.jeju.web.domain.userMenus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jeju.web.domain.BaseTimeEntity;
import com.jeju.web.domain.users.Users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "url" }))
public class UserMenus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 100, nullable = false)
    private String url;

    @Column(length = 1, nullable = false)
    private Boolean readable;

    @Column(length = 1, nullable = false)
    private Boolean writable;

    public void setUsers(Users users) {
        this.users = users;
    }

    @Builder
    public UserMenus(Users users, String url, boolean read, boolean write) {
        this.users = users;
        this.url = url;
        this.userName = users.getUsername();
        this.readable = read;
        this.writable = write;
    }
}
