package com.spk.web.myfirstws.io.repositories;

import com.spk.web.myfirstws.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    UserEntity findByUserId(String userId);
}
