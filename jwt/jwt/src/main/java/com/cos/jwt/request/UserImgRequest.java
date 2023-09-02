package com.cos.jwt.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserImgRequest {
    private Long seq;
    private String originFile;
    private String saveFile;
    private long filePath;


}