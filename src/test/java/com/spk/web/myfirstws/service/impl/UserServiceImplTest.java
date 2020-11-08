package com.spk.web.myfirstws.service.impl;

import com.spk.web.myfirstws.io.entity.UserEntity;
import com.spk.web.myfirstws.io.repositories.UserRepository;
import com.spk.web.myfirstws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    final void testGetUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Sarar");
        userEntity.setUserId("hhsrpk78hgy");
        userEntity.setEncryptedPassword("77hghj8798jki");

        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = userService.getUser("sarar.pk@test.com");

        assertNotNull(userDto);
        assertEquals("Sarar", userDto.getFirstName());

    }

    @Test()
    final void testGetUser_UserNameNotFoundException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser("sarar.pk@test.com");
                }
        );
    }
}
