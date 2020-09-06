package com.jeju.web.security.service;

import java.util.HashSet;
import java.util.Set;

import com.jeju.web.domain.users.Users;
import com.jeju.web.domain.users.UsersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("============= UserDetail 실행 ============== {}", username);

        // TODO Auto-generated method stub
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getHasAuthority().toUpperCase().toString()));
        logger.info("info {} / {} / {}", user.getUsername(), user.getPassword(), grantedAuthorities);
        logger.info("============= UserDetail 종료 ==============");

        logger.info("============= User LoginCheck 변경 시작 ==============");
        user.setLoginCheck(true);
        usersRepository.save(user);
        logger.info("============= User LoginCheck 변경 종료 ==============");
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);

    }

}

class existLogin extends Exception {

    existLogin(String msg) {// 문자열을 매개변수로 받는 생성자

        super(msg);// 조상인 Exception 클래스의 생성자를 호출한다.

    }

}