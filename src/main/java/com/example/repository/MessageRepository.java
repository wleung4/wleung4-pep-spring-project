package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("FROM Message WHERE postedBy = :postedBy")
    List<Message> findAllMessagesByAccountId(@Param("postedBy") Integer account_id);
}
