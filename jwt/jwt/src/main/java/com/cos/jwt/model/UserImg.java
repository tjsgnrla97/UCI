package com.cos.jwt.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long seq;

    private String originFile;
    private String saveFile;
    private String saveFolder;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;



}
