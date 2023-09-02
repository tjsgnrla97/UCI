package com.cos.jwt.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    private String department;
    private String position;
    private String nickname;
    private String roles; //ROLE_USER, ROLE_ADMIN
    private String imgurl;



    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
