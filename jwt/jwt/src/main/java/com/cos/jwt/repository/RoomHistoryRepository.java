package com.cos.jwt.repository;

import com.cos.jwt.model.RoomHistory;
import com.cos.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
    List<RoomHistory> findByUser(User user);
}
