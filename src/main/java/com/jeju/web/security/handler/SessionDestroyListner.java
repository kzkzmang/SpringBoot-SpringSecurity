package com.jeju.web.security.handler;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.User;

public class SessionDestroyListner implements ApplicationListener<SessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        // TODO Auto-generated method stub
        List<SecurityContext> contexts = event.getSecurityContexts();
        if (!contexts.isEmpty()) {
            for (SecurityContext ctx : contexts) {
                User user = (User) ctx.getAuthentication().getPrincipal();
                System.out.println(user);
            }
        }

    }

}
