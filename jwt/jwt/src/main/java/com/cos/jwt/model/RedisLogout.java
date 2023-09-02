package com.cos.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("RedisLogout")
@AllArgsConstructor
@NoArgsConstructor
public class RedisLogout implements Serializable {

    @Indexed //값으로 검색 할 시 추가
    @Id // 키 값
    private String token;

    private String logout;

    @TimeToLive // 만료시간 설정
    private Long expired;


}
