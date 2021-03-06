package com.clogic.resourceserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String hello() {
        return "Hello!";
    }

    @RequestMapping(value = "/private")
    public String privateData() {
        return "Private!";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }


}
