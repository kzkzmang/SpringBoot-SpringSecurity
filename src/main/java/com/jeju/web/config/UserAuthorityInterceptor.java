package com.jeju.web.config;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeju.web.domain.userMenus.UserMenus;
import com.jeju.web.domain.userMenus.UserMenusRepository;
import com.jeju.web.security.service.PrincipalService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserAuthorityInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMenusRepository userMenusRepository;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        logger.info("preHandle 시작");
        // User 페이지 권한 체크 추가 예정
        /**
         * user 유저 정보
         * 
         */
        UserDetails user = new PrincipalService().getDetails();
        if (user != null) {
            Optional<UserMenus> opUserMenus = Optional.ofNullable(userMenusRepository
                    .findByUserNameAndUrl(user.getUsername(), req.getRequestURI().replace("/jeju", "")));

            if (opUserMenus.isPresent()) {
                UserMenus um = opUserMenus.get();
                if (!um.getReadable()) {
                    res.sendError(403, "권한이 없습니다.");
                    logger.info("{} / {} / {} / 권한 여부: {}", user.getUsername(), user.getAuthorities(),
                            req.getRequestURI(), um.getReadable() ? "있음" : "없음");
                }
            } else {
                res.sendError(403, "권한이 없습니다.");
            }
        }

        return super.preHandle(req, res, handler);
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv)
            throws Exception {
        logger.info("postHandle 시작");
    }
}
