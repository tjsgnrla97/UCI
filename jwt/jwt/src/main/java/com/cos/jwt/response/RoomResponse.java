package com.cos.jwt.response;

import com.cos.jwt.model.Room;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
public class RoomResponse {
    String message = null;

    Integer statusCode = null;

    Long roomSeq;
    String hostNickname;
    String title;
    String description;
    int joinNumber;
    Timestamp startTime;
    boolean active;

    public static RoomResponse of(Room room){
        RoomResponse res = new RoomResponse();
        res.setRoomSeq(room.getSeq());
        res.setDescription(room.getDescription());
        res.setHostNickname(room.getHostNickname());
        res.setTitle(room.getTitle());
        res.setJoinNumber(room.getJoinNumber());
        res.setStartTime(room.getStartTime());
//        res.setActive(room.isActive());
        return res;
    }
    public static RoomResponse of(Integer statusCode, String message){
        RoomResponse body = new RoomResponse();
        body.message = message;
        body.statusCode = statusCode;
        return body;
    }

}
