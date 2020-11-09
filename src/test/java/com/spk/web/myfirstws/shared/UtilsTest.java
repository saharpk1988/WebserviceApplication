package com.spk.web.myfirstws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {

    }

    @Test
    final void testGenerateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId.length() == 30);
        assertTrue(!userId.equalsIgnoreCase(userId2));

    }

    @Test
    //@Disabled
    final void testHasTokenExpired() {
        String token = utils.generateEmailVerificationToken("hhsrpk78hgy");
        assertNotNull(token);

        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);

    }


}
