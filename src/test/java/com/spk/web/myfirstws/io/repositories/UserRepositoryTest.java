package com.spk.web.myfirstws.io.repositories;

import com.spk.web.myfirstws.io.entity.AddressEntity;
import com.spk.web.myfirstws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static String userId;

    private static boolean recordsCreated = false;

    @BeforeEach
    void setUp() throws Exception {

        //if (!recordsCreated) createRecords();
    }

    @Test
    final void testGetVerifiedUsers() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageable);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        //assertTrue(userEntities.size() == 2);
    }


    @Test
    final void testFindUserByFirstLastName() {
        String firstName = "Sarar";
        String lastName = "Pk";
        List<UserEntity> users = userRepository.findUserByFirstLastName(firstName, lastName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equalsIgnoreCase(firstName));
    }

    @Test
    final void testFindUserByLastName() {
        String lastName = "Pk";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equalsIgnoreCase(lastName));
    }

    @Test
    final void testFindUsersByKeyword() {
        String keyword = "Sa";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().contains(keyword) || user.getFirstName().contains(keyword));
    }

    @Test
    final void testFindUserFirstNameAndLastNameByKeyword() {
        String keyword = "Sar";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);

        assertTrue(users.size() == 2);

        Object[] user = users.get(0);

        assertTrue(user.length == 2);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);
        assertNotNull(userFirstName);
        assertNotNull(userLastName);
        System.out.println("First Name= " + userFirstName);
        System.out.println("Last Name= " + userLastName);

    }

    @Test
    final void testUpdateUserEmailVerificationStatus() {
        boolean emailVerificationStatus = false;
        userRepository.updateUserEmailVerificationStatus(emailVerificationStatus, userId);

        UserEntity storedDetails = userRepository.findByUserId("100ps87ms");
        boolean storedEmailVerificationStatus = storedDetails.getEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus == emailVerificationStatus);
    }

    @Test
    final void testFindUserEntityByUserId() {

        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    final void testFindUserEntityFullNameById() {
        List<Object[]> records = userRepository.findUserEntityFullNameById(userId);
        assertNotNull(records);

        assertTrue(records.size() == 1);

        Object[] userFullName = records.get(0);
        String firstName = String.valueOf(userFullName[0]);
        String lastName = String.valueOf(userFullName[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    final void testUpdateUserEntityEmailVerificationStatus() {

        boolean emailVerificationStatus = false;
        userRepository.updateUserEntityEmailVerificationStatus(emailVerificationStatus, userId);

        UserEntity userEntity = userRepository.findByUserId(userId);
        assertNotNull(userEntity.getEmailVerificationStatus() == emailVerificationStatus);

    }

    private void createRecords() {
        userId = "100ps87ms";
        //Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Sarar");
        userEntity.setLastName("Pk");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("abcdef");
        userEntity.setEmail("sahar.pk@email.com");
        userEntity.setEmailVerificationStatus(true);

        //Prepare User Addresses
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("abc789khm");
        addressEntity.setCity("Vancouver");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("ABCDER");
        addressEntity.setStreetName("123 abc Street Address");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userRepository.save(userEntity);

        //Prepare Another User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Sarar");
        userEntity2.setLastName("Pk");
        userEntity2.setUserId("ps87ms100");
        userEntity2.setEncryptedPassword("1skpm8");
        userEntity2.setEmail("sarar.sol@email.com");
        userEntity2.setEmailVerificationStatus(true);

        //Prepare User Addresses
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("kmh789abc");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("ABCDER");
        addressEntity2.setStreetName("123 abc Street Address");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);
        userRepository.save(userEntity2);
        recordsCreated = true;
    }

}






