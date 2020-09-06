package com.jeju.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/asd")
    public String asd() {
        return "Home";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/adminOnly")
    public String adminOnly() {
        return "Secret Page";
    }

    @RequestMapping("/login")
    public String login() {

        return "Login";
    }
}
