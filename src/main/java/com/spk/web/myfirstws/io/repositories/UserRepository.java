package com.spk.web.myfirstws.io.repositories;

import com.spk.web.myfirstws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "select * from users u where u.last_name=:lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query(value = "select * from users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);

    @Query(value = "select u.first_name, u.last_name from users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);


    @Transactional
    @Modifying
    @Query(value = "update users u set u.EMAIL_VERIFICATION_STATUS=:status where u.user_id=:id", nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("status") boolean emailVerificationStatus, @Param("id") String userId);


}
