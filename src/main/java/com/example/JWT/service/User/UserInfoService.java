package com.example.JWT.service.User;


import com.example.JWT.model.Doctor;
import com.example.JWT.model.Patient;
import com.example.JWT.model.User;
import com.example.JWT.repository.AdminRepository;
import com.example.JWT.repository.PatientRepository;
import com.example.JWT.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.JWT.model.User> userInfo = userInfoRepository.findByEmail(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));

    }
    public String addUser(Patient userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
       // userInfoRepository.save(userInfo);
        patientRepository.save(userInfo);
        return "User added successfully";
    }
    public List<User> getAllUser(){
        return userInfoRepository.findAll();
    }
    public User getUser(Integer id){
        return userInfoRepository.findById(id).get();
    }
}
