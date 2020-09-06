package com.jeju.web.domain.posts;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;
import java.util.List;

import com.jeju.web.domain.urls.Urls;
import com.jeju.web.domain.urls.UrlsRepository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlsRepositoryTest {

    @Autowired
    UrlsRepository urlsRepository;

    @After
    public void cleanup() {
        // postsRepository.deleteAll();
    }

    @Test
    public void 페이지저장_불러오기() {
        // given
        urlsRepository.save(Urls.builder().url("/adminOnly").hasAuthority("admin").build());
        urlsRepository.save(Urls.builder().url("userOnly/**").hasAuthority("admin,guest").build());

        // when
        List<Urls> urls = urlsRepository.findAll();

        // then
        for (Urls url : urls) {
            System.out.println(url.getUrl());
            System.out.println(url.getHasAuthority());
        }
    }

}
