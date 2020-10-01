package com.spk.web.myfirstws.ui.controller;

import com.spk.web.myfirstws.service.UserService;
import com.spk.web.myfirstws.shared.dto.UserDto;
import com.spk.web.myfirstws.ui.model.request.UserDetailsRequest;
import com.spk.web.myfirstws.ui.model.response.ErrorMessages;
import com.spk.web.myfirstws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserById(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequest userDetails) throws Exception {
        if (userDetails.getFirstName().isEmpty())
            throw new Exception(ErrorMessages.MISSING_REQUESTED_FIELD.getMessage());

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
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
