package com.cos.jwt.request;

import lombok.Data;
import lombok.Getter;

@Data
public class UserJoinRequest { //회원가입 request 요청

    String email;
    String password;
    String username;
    String nickname;
    String department;
    String position;
}
