package com.example.JWT.repository;

import com.example.JWT.model.Doctor;
import com.example.JWT.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
