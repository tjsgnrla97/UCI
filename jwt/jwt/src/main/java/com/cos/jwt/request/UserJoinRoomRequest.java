package com.cos.jwt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRoomRequest {
    String username;
    Long userSeq;
    Long roomSeq;
    boolean state;

    @Override
    public String toString() {
        return "UserJoinRoomRequest{" +
                "username='" + username + '\'' +
                ", userSeq=" + userSeq +
                ", roomSeq=" + roomSeq +
                ", state=" + state +
                '}';
    }
}
