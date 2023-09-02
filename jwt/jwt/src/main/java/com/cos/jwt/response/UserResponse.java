package com.cos.jwt.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Getter
@Setter
public class UserResponse {

    private long seq;
    private String username;
    private String password;
    private String email;
    private String department;
    private String position;
    private String nickname;
    private String roles; //ROLE_USER, ROLE_ADMIN
    private String imgurl;

    String message = null;
    Integer statusCode = null;

public static UserResponse of(String department, String position, String username, String email) {
    UserResponse body = new UserResponse();
    body.department = department;
    body.position = position;
    body.username = username;
    body.email = email;
    return body;
}


    public static UserResponse of(Integer statusCode, String message) {
        UserResponse body = new UserResponse();
        body.statusCode = statusCode;
        body.message = message;
        return body;
    }
}
