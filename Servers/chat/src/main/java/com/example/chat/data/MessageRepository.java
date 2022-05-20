package com.example.chat.data;

import com.example.chat.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("FROM Message ORDER BY timestamp DESC")
    List<Message> findAllOrderByTimestampDesc();
}
