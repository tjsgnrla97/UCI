package com.cos.jwt.repository;

import com.cos.jwt.model.User;
import com.cos.jwt.model.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserImgRepository extends JpaRepository<UserImg, Long> {

    UserImg save(UserImg userImg);

    Optional<UserImg> findByUser(User user);


}
