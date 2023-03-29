package com.shq.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/test/hello",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('test')")
    public String hello(){
        return "hello";
    }

}
