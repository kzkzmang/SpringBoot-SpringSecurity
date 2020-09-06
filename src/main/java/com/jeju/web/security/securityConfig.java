package com.jeju.web.security;

import java.util.List;

import javax.persistence.Version;

import com.jeju.web.domain.urls.Urls;
import com.jeju.web.domain.urls.UrlsRepository;
import com.jeju.web.security.handler.AuthProvider;
import com.jeju.web.security.handler.LoginFailHandler;
import com.jeju.web.security.handler.LogoutHandlerImpl;
import com.jeju.web.security.service.UserDetailServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UrlsRepository urlsRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        setAntMatchers(http, "ROLE_");
        // users 페이지 권한 허용
        // http.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=incorrect").permitAll()
        http.csrf().disable().formLogin().loginPage("/login").failureHandler(failureHandler()).permitAll()
                .defaultSuccessUrl("/").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(new LogoutHandlerImpl()).invalidateHttpSession(true).permitAll().logoutSuccessUrl("/")
                .and().authenticationProvider(authProvider());
        // 중복로그인
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/login")
                .sessionRegistry(sessionRegistry());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    // setAntMatchers
    protected void setAntMatchers(HttpSecurity http, String rolePrefix) throws Exception {
        List<Urls> urls = urlsRepository.findAll();
        logger.info("크기 {}", urls.size());
        logger.info("비밀번호 admin {}", passwordEncoder().encode("admin"));
        if (urls.size() == 0) {
            urlsRepository.save(Urls.builder().url("/adminOnly").hasAuthority("admin").build());
            urlsRepository.save(Urls.builder().url("userOnly/**").hasAuthority("admin,guest").build());
            urls = urlsRepository.findAll();
        }
        // List 결과 확인
        for (Urls url : urls) {
            logger.info("url: {} / athority: {}", url.getUrl(), url.getHasAuthority());
            // 권한 처리
            // 쉼표(,)로 구분된 권한 정보를 분리 후 배열로 저장
            String[] roles = url.getHasAuthority().toString().split(",");
            // 권한 앞에 접두사(rolePrefix) 붙임
            for (int i = 0; i < roles.length; i++) {
                roles[i] = rolePrefix + roles[i].toUpperCase();
            }
            // DB에서 url을 읽어올 때 앞에 '/'이 없다면
            // 앞에 '/'를 넣어준다.
            String urlString = url.getUrl().toString();
            if (urlString.charAt(0) != '/') {
                urlString = "/" + urlString;
            }
            // url, 권한 정보를 넣는다.
            http.authorizeRequests().antMatchers(urlString).hasAnyAuthority(roles);
        }

        String[] whiteList = { "/util/**", "/css/**", "/images/**", "/script/**", "/user/**" };

        // whiteList 권한 허용
        for (String list : whiteList) {
            http.authorizeRequests().antMatchers(list).permitAll();
        }
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailHandler();
    }

    @Bean
    public AuthProvider authProvider() {
        return new AuthProvider();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
