package com.shq.security.mybatisTest;

import com.shq.security.entities.User;
import com.shq.security.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestMapper {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testMapper(){
        User user = User.builder().userName("宋恒强")
                .password("123456")
                .phonenumber("15292242950")
                .createTime(new Date())
                .delFlag(0).build();
        userMapper.insert(user);
    }

    @Test
    public void testMapperSelect(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
