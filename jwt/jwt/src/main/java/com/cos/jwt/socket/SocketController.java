package com.cos.jwt.socket;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class SocketController {

//receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive/{roomTitle}")
            // /send로 메시지를 반환합니다.
    @SendTo("/send/{roomTitle}")
    //SocketHandler는 1)/receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    //정의한 SocketVO를 1)인자값, 2)반환값으로 사용합니다.
    public SocketVO SocketHandler(@DestinationVariable String roomTitle, SocketVO socketVO){
        String type = socketVO.getType();
        String userNickname = socketVO.getUserNickname();
        String content = socketVO.getContent();
        SocketVO result = new SocketVO(type,userNickname,content);
        return result;
    }
}
