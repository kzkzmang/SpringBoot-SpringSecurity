package com.jeju.web.domain.posts;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;
import java.util.Optional;
import com.jeju.web.domain.userMenus.UserMenus;
import com.jeju.web.domain.userMenus.UserMenusRepository;
import com.jeju.web.domain.users.Users;
import com.jeju.web.domain.users.UsersRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMenusRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMenusRepository userMenusRepository;

    // @Before
    // public void cleanup() {
    // userMenusRepository.deleteAll();
    // }

    @Test
    public void 유저와메뉴테이블외래키() {
        // given
        Optional<Users> user = usersRepository.findByUsername("admin");
        if (user.isPresent()) {
            Users u = user.get();
            UserMenus usermenus = new UserMenus(u, "/test", true, true);
            UserMenus sum = userMenusRepository.save(usermenus);
            logger.info("{}", sum.getUrl());
        }

        // when
        List<UserMenus> userMenusTestList = userMenusRepository.findAll();

        // then
        UserMenus userMenusTest = userMenusTestList.get(0);
        assertThat(userMenusTest.getUrl(), is("/test"));

    }

    @Test
    public void 유저정보로메뉴검색() {
        // given
        Optional<Users> user = usersRepository.findByUsername("admin");
        Users u = user.get();

        // when
        UserMenus usermenu = userMenusRepository.findByUserNameAndUrl(u.getUsername(), "/test");

        // then
        assertThat(usermenu.getReadable(), is(true));
    }
}
