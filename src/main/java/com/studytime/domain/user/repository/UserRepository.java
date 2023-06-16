package com.studytime.domain.user.repository;

import com.studytime.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findByUserAccountAndPassword(String userAccount, String password);
}
