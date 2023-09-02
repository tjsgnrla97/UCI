package com.cos.jwt.controller;

import com.cos.jwt.model.Room;
import com.cos.jwt.request.CreateRoomRequest;
import com.cos.jwt.request.UserJoinRoomRequest;
import com.cos.jwt.response.RoomResponse;
import com.cos.jwt.response.TimeResponse;
import com.cos.jwt.response.UserResponse;
import com.cos.jwt.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin("*")
public class RoomController {
    @Autowired
    RoomService roomService;
    //방 생성
    @PostMapping("/create")
    public ResponseEntity createRoom(@RequestBody CreateRoomRequest createRoomRequest){
        roomService.createRoom(createRoomRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
    //방 제목으로 방 번호 받아오기
    @GetMapping("/roomSeq/{title}")
    public ResponseEntity<? extends RoomResponse> roomSeq(@PathVariable("title") String title){
        System.out.println(title);
        Room room = roomService.roomSeq(title);

        return ResponseEntity.status(201).body(RoomResponse.of(room));
    }
    //방 번호로 방 삭제하기
    @DeleteMapping("/{roomSeq}")
    public ResponseEntity deleteRoom(@PathVariable("roomSeq") Long roomSeq){
        roomService.deleteRoom(roomSeq);
        return new ResponseEntity(HttpStatus.OK);
    }

    //전체 방 리스트 조회
    @GetMapping
    public @ResponseBody List<Room> findAllRoom(){
        List<Room> roomList = roomService.searchRoomList();
        return roomList;
    }

    //방 접속하기
    @PostMapping("/join")
    public ResponseEntity<? extends RoomResponse> joinRoom(@RequestBody UserJoinRoomRequest userJoinRoomRequest){
        System.out.println(userJoinRoomRequest.toString());
        roomService.joinRoom(userJoinRoomRequest);
        return ResponseEntity.status(201).body(RoomResponse.of(201, "Success"));
    }

    //방 떠나기
    @PutMapping("/leave/{roomSeq}/{userSeq}")
    public ResponseEntity leaveRoom(@PathVariable("roomSeq") Long roomSeq, @PathVariable("userSeq") Long userSeq){
        roomService.leaveRoom(roomSeq,userSeq);
        return new ResponseEntity(HttpStatus.OK);
    }

    //방 접속시간
    @GetMapping("/time/{roomSeq}")
    public @ResponseBody List<TimeResponse> getTime(@PathVariable("roomSeq") Long roomSeq){
        List<TimeResponse> result = roomService.getTime(roomSeq);
        Collections.sort(result);
        return result;
    }
    //방 이름 중복검사
    @GetMapping("/title/{title}")
    public ResponseEntity<? extends UserResponse> titleDuplication(@PathVariable("title") String title){
        Room room = roomService.getRoomByTitle(title);

        if(room==null){
            return ResponseEntity.status(200).body(UserResponse.of(200,"Success"));
        }
        else{
            return ResponseEntity.status(200).body(UserResponse.of(200, "Fail"));
        }
    }
    //방 게임 시작
    @PutMapping("/game/{roomSeq}")
    public ResponseEntity gameStartRoom(@PathVariable("roomSeq") Long roomSeq){
        roomService.gameStartRoom(roomSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //방 게임 종료
    @PutMapping("/stay/{roomSeq}")
    public ResponseEntity gameStayRoom(@PathVariable("roomSeq") Long roomSeq){
        roomService.gameStayRoom(roomSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
