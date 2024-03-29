package com.example.JWT.repository;

import com.example.JWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<User,Integer> {

    @Query("SELECT e FROM User e WHERE e.email = :email ")
    Optional<User> findByEmail(String email);


}