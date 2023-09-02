package com.cos.jwt.repository;

import com.cos.jwt.model.Room;
import com.cos.jwt.model.User;
import com.cos.jwt.model.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom,Long> {
    Optional<UserRoom> findByUserAndRoom(User user, Room room);

    Optional<UserRoom> findBySeq(Long seq);

    List<UserRoom> findUserRoomByRoom(Room room);

}
