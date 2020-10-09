package com.spk.web.myfirstws.ui.controller;

import com.spk.web.myfirstws.exceptions.UserServiceException;
import com.spk.web.myfirstws.service.AddressService;
import com.spk.web.myfirstws.service.UserService;
import com.spk.web.myfirstws.shared.dto.AddressDto;
import com.spk.web.myfirstws.shared.dto.UserDto;
import com.spk.web.myfirstws.ui.model.request.UserDetailsRequest;
import com.spk.web.myfirstws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    AddressService addressesService;

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = userService.getUserById(id);
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);
        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequest userDetails) throws Exception {

        if (userDetails.getFirstName().isEmpty())
            throw new NullPointerException(ErrorMessages.MISSING_REQUESTED_FIELD.getMessage());
        ModelMapper modelMapper = new ModelMapper();
        UserRest returnValue = new UserRest();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);
        return returnValue;
    }

    @PutMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequest userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(id, userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(Operations.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(Operations.SUCESS.name());
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "7") int limit) {
        ModelMapper modelMapper = new ModelMapper();
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> userDtoList = userService.getUsers(page, limit);
        for (UserDto userDto : userDtoList) {
            UserRest userModel = modelMapper.map(userDto, UserRest.class);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    //https://localhost:8443/my-first-ws/users/<userId>/addresses
    @GetMapping(path = "/{id}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
        List<AddressesRest> returnValue = new ArrayList<>();
        List<AddressDto> addressList = addressesService.getAddresses(id);
        if (addressList != null && !addressList.isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            returnValue = modelMapper.map(addressList, listType);
        }
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id)
                .withRel("user");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(id))
                .withSelfRel();
        return CollectionModel.of(returnValue, userLink, selfLink);
    }

    //https://localhost:8443/my-first-ws/users/<userId>/addresses/<addressId>
    @GetMapping(path = "/{id}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<AddressesRest> getUserAddress(@PathVariable String id, @PathVariable String addressId) {
        if (userService.getUserById(id) == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getMessage());
        AddressDto addressDto = addressService.getAddress(addressId);
        ModelMapper modelMapper = new ModelMapper();
        AddressesRest returnValue = modelMapper.map(addressDto, AddressesRest.class);

        //https://localhost:8443/my-first-ws/users/<userId>
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        //https://localhost:8443/my-first-ws/users/<userId>/addresses
        Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserAddresses(id))
                .withRel("addresses");
        //https://localhost:8443/my-first-ws/users/<userId>/addresses/<addressId>
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserAddress(id, addressId))
                //.slash(id).slash("addresses").slash(addressId)
                .withSelfRel();

        //returnValue.add(userLink);
        //returnValue.add(userAddressesLink);
        //returnValue.add(selfLink);

        return EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink));

    }

}
