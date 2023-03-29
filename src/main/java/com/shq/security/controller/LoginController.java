package com.shq.security.controller;

import com.shq.security.entities.User;
import com.shq.security.service.LoginService;
import com.shq.security.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseResult login(@RequestBody User user){
        Map<String, Object> login = loginService.login(user);
        return new ResponseResult(200,"success",login);
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.POST)
    public ResponseResult logout(){
        loginService.logout();
        return new ResponseResult(200,"success",null);
    }
}
