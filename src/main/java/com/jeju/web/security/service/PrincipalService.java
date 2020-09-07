package com.jeju.web.security.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalService {

    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object getPrincipal() {
        Optional<Object> principal = Optional.ofNullable(auth.getPrincipal());

        return principal.isPresent() ? principal.get() : null;
    }

    public UserDetails getDetails() {
        Optional<Object> userDetails = Optional.ofNullable(auth.getDetails());
        return userDetails.isPresent() ? (UserDetails) userDetails.get() : null;
    }
}
