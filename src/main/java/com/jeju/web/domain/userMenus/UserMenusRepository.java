package com.jeju.web.domain.userMenus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMenusRepository extends JpaRepository<UserMenus, Long> {
    UserMenus findByUserNameAndUrl(String username, String url);
}
