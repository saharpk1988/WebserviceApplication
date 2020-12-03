package com.spk.web.myfirstws;

import com.spk.web.myfirstws.io.entity.AuthorityEntity;
import com.spk.web.myfirstws.io.entity.RoleEntity;
import com.spk.web.myfirstws.io.entity.UserEntity;
import com.spk.web.myfirstws.io.repositories.AuthorityRepository;
import com.spk.web.myfirstws.io.repositories.RoleRepository;
import com.spk.web.myfirstws.io.repositories.UserRepository;
import com.spk.web.myfirstws.shared.Roles;
import com.spk.web.myfirstws.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUserSetUp {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
        RoleEntity roleUser = createRoles(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRoles(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) return;

        if (userRepository.findUserByEmail("admin@test.com") != null) return;
        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setEmail("admin@test.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setUserId(utils.generateUserId(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("admin123"));
        adminUser.setRoles(Arrays.asList(roleAdmin));


        userRepository.save(adminUser);
    }

    @Transactional
    protected AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    protected RoleEntity createRoles(
            String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }


}
