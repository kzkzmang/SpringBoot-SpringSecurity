package com.jeju.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping(method = { RequestMethod.GET }, value = "/")
    public String Home() {
        return "Home";
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/jeju/{uri}")
    public String dynamicController(@PathVariable String uri) {
        String jsp = uri.substring(0, 1).toUpperCase() + uri.substring(1);

        return jsp;
    }

    @ResponseBody
    @RequestMapping("/jeju/test")
    public String test() {
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/jeju/adminOnly")
    public String adminOnly() {
        return "Secret Page";
    }

    @RequestMapping("/login")
    public String login() {

        return "Login";
    }
}
