package com.spk.web.myfirstws.ui.controller;

import com.spk.web.myfirstws.ui.model.request.UserDetailsRequest;
import com.spk.web.myfirstws.ui.model.response.UserRest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @GetMapping
    public String getUser(){
        return "first web app get user was called.";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequest userDetails){
        return null;
    }

    @PutMapping
    public String updateUser(){
        return "first web app update user was called.";
    }
    @DeleteMapping
    public String deleteUser(){
        return "first web app delete user was called.";
    }

}
