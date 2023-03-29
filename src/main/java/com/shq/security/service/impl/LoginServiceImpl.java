package com.shq.security.service.impl;

import com.shq.security.domain.LoginUser;
import com.shq.security.entities.User;
import com.shq.security.service.LoginService;
import com.shq.security.utils.JwtUtil;
import com.shq.security.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;

    @Override
    public Map<String, Object> login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //认证通过，获取用户信息
        LoginUser userInfo = (LoginUser) authenticate.getPrincipal();
        //根据userId生成jwtToken
        String jwtToken = JwtUtil.createJWT(userInfo.getUser().getId().toString());
        Map<String,Object> result = new HashMap<>();
        result.put("jwtToken",jwtToken);
        //将用户信息存入redis
        String loginKey = "login"+userInfo.getUser().getId();
        redisCache.setCacheObject(loginKey,userInfo);
        return result;
    }

    @Override
    public void logout() {
        //从securityContextholder中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser userInfo = (LoginUser) authentication.getPrincipal();
        //清除redis
        String loginKey = "login"+userInfo.getUser().getId();
        redisCache.deleteObject(loginKey);
    }
}
