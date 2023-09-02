package com.cos.jwt.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketVO {
    private String type;
    private String userNickname;
    private String content;
}
