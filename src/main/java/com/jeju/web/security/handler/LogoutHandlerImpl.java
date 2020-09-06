package com.jeju.web.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * LogoutHandler
 */
public class LogoutHandlerImpl implements LogoutHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
        logger.info("로그아웃 되었습니다");
    }
}