package com.spk.web.myfirstws.io.repositories;

import com.spk.web.myfirstws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    UserEntity findByUserId(String userId);

    UserEntity findUserByEmailVerificationToken(String token);

    @Query(value = "select * from users u where u.EMAIL_VERIFICATION_STATUS= 'true' ",
            countQuery = "select count(*) from users u where u.EMAIL_VERIFICATION_STATUS= 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageable);

    @Query(value = "select * from users u where u.first_name= ?1 and u.last_name=?2", nativeQuery = true)
    List<UserEntity> findUserByFirstLastName(String firstName, String lastName);


}
