package com.cos.jwt.repository;

import com.cos.jwt.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Nullable
    Optional<Room> findByTitle(String title);

    Optional<Room> findBySeq(Long roomSeq);

}
