package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query("FROM Account WHERE username = :username")
    Account findByUsername(@Param("username") String username);

    @Query("FROM Account WHERE id = :id")
    Optional<Account> findById(@Param("id") Integer id);
}
