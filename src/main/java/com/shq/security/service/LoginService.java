package com.shq.security.service;

import com.shq.security.entities.User;

import java.util.Map;

public interface LoginService {

    Map<String,Object> login(User user);

    void logout();

}
