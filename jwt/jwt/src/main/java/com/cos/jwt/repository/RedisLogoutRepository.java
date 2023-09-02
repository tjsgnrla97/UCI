package com.cos.jwt.repository;

import com.cos.jwt.model.RedisLogout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisLogoutRepository extends CrudRepository<RedisLogout, String> {

    Optional<RedisLogout> findByToken(String token);

}
