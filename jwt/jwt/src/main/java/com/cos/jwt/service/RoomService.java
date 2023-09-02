package com.cos.jwt.service;

import com.cos.jwt.model.Room;
import com.cos.jwt.model.RoomHistory;
import com.cos.jwt.model.User;
import com.cos.jwt.model.UserRoom;
import com.cos.jwt.repository.RoomHistoryRepository;
import com.cos.jwt.repository.RoomRepository;
import com.cos.jwt.repository.UserRepository;
import com.cos.jwt.repository.UserRoomRepository;
import com.cos.jwt.request.CreateRoomRequest;
import com.cos.jwt.request.UserJoinRoomRequest;
import com.cos.jwt.response.TimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    @Autowired
    RoomHistoryRepository roomHistoryRepository;

    public void createRoom(CreateRoomRequest createRoomRequest) {
        Room room = new Room();
        RoomHistory roomHistory = new RoomHistory();

        room.setHostNickname(createRoomRequest.getHostNickname());
        room.setTitle(createRoomRequest.getTitle());
        room.setDescription(createRoomRequest.getDescription());
        room.setJoinNumber(createRoomRequest.getJoinNumber());
        room.setActive(false);
        roomRepository.save(room);
        Optional<User> user = userRepository.findByNickname(createRoomRequest.getHostNickname());
//        Optional<Room> afterRoom = roomRepository.findByTitle(createRoomRequest.getTitle());
        roomHistory.setUser(user.get());
//        roomHistory.setRoom(afterRoom.get());
        roomHistory.setAction(0);
        roomHistoryRepository.save(roomHistory);
    }

    public Room roomSeq(String title) {
        Room room = new Room();
        Optional<Room> findRoom = roomRepository.findByTitle(title);
        room.setSeq(findRoom.get().getSeq());
        return findRoom.get();
    }

    public void deleteRoom(Long roomSeq) {
        Optional<Room> room = roomRepository.findBySeq(roomSeq);
        //deleteUserRoom(room.get());
        roomRepository.delete(room.get());
    }

    public List<Room> searchRoomList() {
        return roomRepository.findAll();
    }

    public void joinRoom(UserJoinRoomRequest userJoinRoomRequest) {
        UserRoom userRoom = new UserRoom();
        RoomHistory roomHistory = new RoomHistory();

        Optional<User> user = userRepository.findBySeq(userJoinRoomRequest.getUserSeq());
        Optional<Room> room = roomRepository.findBySeq(userJoinRoomRequest.getRoomSeq());

        Optional<UserRoom> check = userRoomRepository.findByUserAndRoom(user.get(), room.get());
//        int joinNum = room.get().getJoinNumber();
        if(check.isPresent()){
            Optional<UserRoom> update = userRoomRepository.findBySeq(check.get().getSeq());
            System.out.println(check.get().getSeq());
            System.out.println(update.toString());
            System.out.println("수정");
            System.out.println(user.get().toString());
            if(update.isPresent()){
                userRoom = update.get();
                userRoom.setState(true);
                userRoom.setUser(user.get());
                userRoom.setRoom(room.get());
                userRoom.setUsername(user.get().getUsername());

                userRoomRepository.save(userRoom);
            }
        }
        else{
            userRoom.setState(true);
            System.out.println("추가");
            System.out.println(user.get().toString());
            if(user.isPresent()){
                userRoom.setUser(user.get());
            }
            if(room.isPresent()){
                userRoom.setRoom(room.get());
            }
            userRoom.setUsername(user.get().getUsername());

            userRoomRepository.save(userRoom);
        }
//        roomHistory.setRoom(room.get());
        roomHistory.setUser(user.get());
        roomHistory.setAction(1);
        roomHistoryRepository.save(roomHistory);
    }

    public void leaveRoom(Long roomSeq, Long userSeq) {
        RoomHistory roomHistory = new RoomHistory();
        Optional<User> user = userRepository.findBySeq(userSeq);
        Optional<Room> room = roomRepository.findBySeq(roomSeq);

        Optional<UserRoom> userRoom = userRoomRepository.findByUserAndRoom(user.get(), room.get());

        if (userRoom.isPresent()){
            UserRoom update = userRoom.get();

            update.setState(false);

            Duration duration = Duration.between(LocalTime.now(), update.getJoinTime());

            update.setSumTime(Math.abs(duration.getSeconds())+update.getSumTime());
            userRoomRepository.save(update);
            roomHistory.setUser(user.get());
            roomHistory.setAction(2);
            roomHistoryRepository.save(roomHistory);
        }
    }

    public List<TimeResponse> getTime(Long roomSeq) {
        List<UserRoom> userRoomList = getUserRoomList(roomSeq);
        List<TimeResponse> result = new ArrayList<>();

        for(int i=0; i<userRoomList.size(); i++){
            Duration duration;
            if(userRoomList.get(i).getState()){
                duration = Duration.between(LocalTime.now(), userRoomList.get(i).getJoinTime());

                long diff = Math.abs(duration.getSeconds())+userRoomList.get(i).getSumTime();
                TimeResponse timeResponse = TimeResponse.res(diff, userRoomList.get(i).getUser().getSeq(),userRoomList.get(i).getUsername());
                result.add(timeResponse);
            }
            else{
                long diff = userRoomList.get(i).getSumTime();
                TimeResponse timeResponse = TimeResponse.res(diff, userRoomList.get(i).getUser().getSeq(),userRoomList.get(i).getUsername());
                result.add(timeResponse);
            }
        }
        return result;
    }

    private List<UserRoom> getUserRoomList(Long roomSeq) {
        Optional<Room> room = roomRepository.findBySeq(roomSeq);

        List<UserRoom> userRoomList = userRoomRepository.findUserRoomByRoom(room.get());
        return  userRoomList;
    }

    public Room getRoomByTitle(String title) {
        Room room = roomRepository.findByTitle(title).orElse(null);
        if(room!=null) {
            System.out.println(room.toString());
        }
        return room;
    }

    public void gameStartRoom(Long roomSeq) {
        Room room = roomRepository.findBySeq(roomSeq).get();
        if (room!=null){
            System.out.println(room.toString());
        }
        room.setActive(true);
        roomRepository.save(room);
    }

    public void gameStayRoom(Long roomSeq) {
        Room room = roomRepository.findBySeq(roomSeq).get();
        if (room!=null){
            System.out.println(room.toString());
        }
        room.setActive(false);
        roomRepository.save(room);
    }
}
