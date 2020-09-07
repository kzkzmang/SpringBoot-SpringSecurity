package com.jeju.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.valueOf(status.toString());
        String msg = "";

        req.setAttribute("status", statusCode);
        switch (statusCode) {
            case 403:
                msg = "권한이 없습니다.";
                break;
            case 404:
                msg = "페이지를 찾을 수 없습니다.";
            default:
                msg = "오류가 발생하였습니다.";
        }

        req.setAttribute("message", msg);
        req.setAttribute("submessage", "오류가 지속적으로 발생시 관리자에게 문의해주세요.");
        return "error";
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return "/error";
    }

}
