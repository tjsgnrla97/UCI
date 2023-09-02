package com.cos.jwt.repository;

import com.cos.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    Optional<User> findBySeq(Long userSeq);

    Optional<User> findByNickname(String hostNickname);
}
