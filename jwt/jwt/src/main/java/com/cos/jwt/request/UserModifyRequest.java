package com.cos.jwt.request;

import lombok.Data;

@Data
public class UserModifyRequest {

    String email;
    String username;
    String department;
    String position;

}
