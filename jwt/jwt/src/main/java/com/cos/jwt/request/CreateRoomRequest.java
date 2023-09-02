package com.cos.jwt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {

    String hostNickname;
    String title;
    String description;
    int joinNumber;
//    boolean active;
}
