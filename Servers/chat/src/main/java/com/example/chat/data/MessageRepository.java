package com.example.chat.data;

import com.example.chat.data.model.Message;
import com.example.chat.room.MessageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT id, " +
                    "created, " +
                    "message, " +
                    "created_by as createdBy, " +
                    "profile_name as profileName " +
                    "FROM message ORDER BY created DESC")
    List<MessageResponse> findAllOrderByTimestampDesc();

    @Query(
            nativeQuery = true,
            value = "SELECT id, " +
                    "created, " +
                    "message, " +
                    "created_by as createdBy, " +
                    "profile_name as profileName " +
                    "FROM message WHERE id=?1")
    Optional<MessageResponse> findResponseById(Long id);
}
