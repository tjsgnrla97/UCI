package com.cos.jwt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq;

    String username;
    Boolean state;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userSeq")
    User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomSeq")
    Room room;

    LocalTime joinTime;

    long sumTime;

    @PrePersist
    public void createdAt(){
        this.joinTime = LocalTime.now();
        this.sumTime=0;
    }

    @PreUpdate
    public void updatedAt(){
        this.joinTime = LocalTime.now();
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "seq=" + seq +
                ", username='" + username + '\'' +
                ", state=" + state +
                ", user=" + user +
                ", room=" + room +
                ", joinTime=" + joinTime +
                ", sumTime=" + sumTime +
                '}';
    }
}
