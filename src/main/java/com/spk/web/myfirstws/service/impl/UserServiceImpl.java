package com.spk.web.myfirstws.service.impl;

import com.spk.web.myfirstws.UserRepository;
import com.spk.web.myfirstws.io.entity.UserEntity;
import com.spk.web.myfirstws.service.UserService;
import com.spk.web.myfirstws.shared.Utils;
import com.spk.web.myfirstws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        //First we copy information from userDto in the userEntity and for this to work the fields in UserDto class must match UserEntity class:
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        if (userRepository.findUserByEmail(userDto.getEmail()) != null)
            throw new RuntimeException("This user already exists.");
        String publicUserId = utils.generateUserId(30);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(publicUserId);


        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        return returnValue;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
