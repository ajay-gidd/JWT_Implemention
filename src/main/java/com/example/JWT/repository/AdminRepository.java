package com.example.JWT.repository;

import com.example.JWT.model.Doctor;
import com.example.JWT.model.Hospital;
import com.example.JWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Hospital,Long> {
    @Query("SELECT e FROM User e WHERE e.email = :email ")
    Optional<Hospital> findByEmail(String email);
}
