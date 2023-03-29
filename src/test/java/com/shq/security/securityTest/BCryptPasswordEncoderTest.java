package com.shq.security.securityTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class BCryptPasswordEncoderTest {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testPasswordEncoder(){
        //对明文密码加密
        String encode = bCryptPasswordEncoder.encode("123456");
        String encode2 = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        System.out.println(encode2);
    }

    @Test
    public void testPasswordMatch(){
        boolean matches = bCryptPasswordEncoder.matches("123456",
                "$2a$10$rwXdTii/A8zRTHilMGYWyOslIeot2xFmM3f7/OOT0dZDzlPNM68j6");
        System.out.println(matches);
    }

}
