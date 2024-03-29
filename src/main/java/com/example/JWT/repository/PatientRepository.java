package com.example.JWT.repository;

import com.example.JWT.model.Patient;
import com.example.JWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
