package com.jeju.web.security.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class LoginFailHandler implements AuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
        logger.error("오류!!!! {} ", exception);

        request.setAttribute("title", "로그인 실패");
        if (exception instanceof AuthenticationServiceException) {
            request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");

        } else if (exception instanceof BadCredentialsException) {
            request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");

        } else if (exception instanceof LockedException) {
            request.setAttribute("loginFailMsg", "잠긴 계정입니다.");

        } else if (exception instanceof DisabledException) {
            request.setAttribute("loginFailMsg", "비활성화된 계정입니다..");

        } else if (exception instanceof AccountExpiredException) {
            request.setAttribute("loginFailMsg", "만료된 계정입니다..");

        } else if (exception instanceof CredentialsExpiredException) {
            request.setAttribute("loginFailMsg", "비밀번호가 만료되었습니다.");
        } else if (exception instanceof SessionAuthenticationException) {
            String id = request.getParameter("username");
            String pwd = request.getParameter("password");

            request.setAttribute("loginExist", true);
            request.setAttribute("username", id);
            request.setAttribute("password", pwd);
            request.setAttribute("loginFailMsg", request.getParameter("username") + "중복 로그인입니다.");
            HttpSession session = request.getSession();
            logger.info("{}", session);
        }

        // 로그인 페이지로 다시 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login?error=incorrect");
        dispatcher.forward(request, response);
    }

}
