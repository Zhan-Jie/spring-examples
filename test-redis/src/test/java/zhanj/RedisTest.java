package zhanj;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    RedisService redisService;

    @Before
    public void setUp() {

    }

    @Test
    public void get() {
        User user = new User("zhanj", 24);
        redisService.add("userByName:" + user.getName(), user, 10L);
        List<User> list = new ArrayList<>();
        list.add(user);
        redisService.add("list", list, 300L);
        User user1 = redisService.get("userByName:zhanj");
        Assert.notNull(user1, "user is null");
        List<User> list2 = redisService.getUserList("list");
        Assert.notNull(list2, "list is null");
    }
}