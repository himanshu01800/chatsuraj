package org.example.Repository;

import org.example.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Custom query to fetch messages between two users
    @Query("SELECT m FROM MessageEntity m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender)")
    List<Message> findMessages(@Param("sender") String sender, @Param("receiver") String receiver);
}
