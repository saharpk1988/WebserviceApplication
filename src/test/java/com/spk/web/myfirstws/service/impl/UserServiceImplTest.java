package com.spk.web.myfirstws.service.impl;

import com.spk.web.myfirstws.io.entity.AddressEntity;
import com.spk.web.myfirstws.io.entity.UserEntity;
import com.spk.web.myfirstws.io.repositories.UserRepository;
import com.spk.web.myfirstws.shared.AmazonSES;
import com.spk.web.myfirstws.shared.Utils;
import com.spk.web.myfirstws.shared.dto.AddressDto;
import com.spk.web.myfirstws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

    String userId = "hhsrpk78hgy";
    String encryptedPassword = "77hghj8798jki";
    UserEntity userEntity;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Sarar");
        userEntity.setLastName("Pk");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail("sarar.pk@test.com");
        userEntity.setEmailVerificationToken("7htfghg8970jh");
        userEntity.setAddresses(getAddressesEntity());
    }

    @Test
    final void createUserTest() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("hgftyi7899");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Sarar");
        userDto.setLastName("Pk");
        userDto.setPassword("1234567");
        userDto.setEmail("sarar.pk@test.com");
        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), userDto.getLastName());
        assertEquals(userEntity.getAddresses().size(), storedUserDetails.getAddresses().size());
        verify(utils, times(storedUserDetails.getAddresses().size())).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("1234567");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    private List<AddressDto> getAddressesDto() {
        AddressDto shippingAddressDto = new AddressDto();
        shippingAddressDto.setType("shipping");
        shippingAddressDto.setCity("Vancouver");
        shippingAddressDto.setCountry("Canada");
        shippingAddressDto.setPostalCode("ABC123");
        shippingAddressDto.setStreetName("123 Street name");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("123 Street name");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(shippingAddressDto);
        addresses.add(billingAddressDto);
        return addresses;
    }

    private List<AddressEntity> getAddressesEntity() {
        List<AddressDto> addresses = getAddressesDto();
        Type addressesEntity = new TypeToken<List<AddressEntity>>() {
        }.getType();
        return new ModelMapper().map(addresses, addressesEntity);
    }

    @Test
    final void testGetUser() {

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
