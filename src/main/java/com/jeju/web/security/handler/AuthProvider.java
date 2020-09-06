package com.jeju.web.security.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.jeju.web.domain.users.Users;
import com.jeju.web.domain.users.UsersRepository;
import com.jeju.web.security.service.UserDetailServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.FindByIndexNameSessionRepository;

public class AuthProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub

        // 유저 정보 가져옴 없으면 Exception 반환
        String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailServiceImpl.loadUserByUsername(id);
        // 중복로그인 상태 설정 start
        boolean flag = false;

        if (password.indexOf("auth|") != -1) {
            flag = true;
        }
        // 중복로그인 상태 설정 end
        if (flag) {
            logger.info(password.replaceAll("auth\\|", ""));
            password = password.replaceAll("auth\\|", "");
        }
        if (!isAuthPassword(password, user.getPassword())) {
            throw new BadCredentialsException("계정정보가 일치하지 않거나 비밀번호가 틀렸습니다.");
        }

        logger.info("==========중복 로그인 프로세스 start=========={}", password);
        // 중복로그인 진행

        if (sessionRegistry.getAllPrincipals().size() != 0) {
            for (Object p : sessionRegistry.getAllPrincipals()) {
                // 세션정보 가져옴
                // 로그인된 사용자 있는지 검사
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(p, false);
                for (SessionInformation sessionInformation : sessionInformations) {
                    logger.info("SESSION ===== {} /{}", sessionInformation.getSessionId(),
                            sessionInformation.getPrincipal());
                    if (sessionInformation.getPrincipal().equals(p) && !sessionInformation.getSessionId().isEmpty()) {
                        // 중복로그인 상태

                        if (flag) {
                            // 이전 세션 초기화
                            logger.info(password.replaceAll("auth\\|", ""));
                            password = password.replaceAll("auth\\|", "");
                            sessionRegistry.getSessionInformation(sessionInformation.getSessionId()).expireNow();
                        } else {
                            throw new SessionAuthenticationException("이미 로그인되어있습니다.");
                        }
                    }

                }
            }
        }
        logger.info("==========중복 로그인 프로세스 end==========");

        // logger.info("id: {} / pwd: {}", id,
        // isAuthPassword(password,
        // "$2a$10$LbYaXu5BZX0qH9qQNQ8.serRYvUi/F6BXcGQP6.YmWJ.VRsP.ddk."));
        // logger.info("{}", user);

        // throw new UsernameNotFoundException("유저 없음");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password,
                user.getAuthorities());
        token.setDetails(user);
        return token;
    }

    // 계정 확인 함수
    public boolean isAuthPassword(String password, String userPassword) {
        return passwordEncoder().matches(password, userPassword);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
