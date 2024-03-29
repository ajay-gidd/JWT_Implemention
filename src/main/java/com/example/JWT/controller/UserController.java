package com.example.JWT.controller;


import com.example.JWT.config.JwtService;
import com.example.JWT.dto.AuthRequest;
import com.example.JWT.model.Doctor;
import com.example.JWT.model.Hospital;
import com.example.JWT.model.Patient;
import com.example.JWT.model.User;
import com.example.JWT.repository.AdminRepository;
import com.example.JWT.repository.DoctorRepository;
import com.example.JWT.repository.PatientRepository;
import com.example.JWT.repository.UserInfoRepository;
import com.example.JWT.service.User.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security tutorials !!";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody Patient userInfo){
        return userInfoService.addUser(userInfo);

    }
    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
//    @GetMapping("/getUsers")
//    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
//    public List<User> getAllUsers(){
//        return userInfoService.getAllUser();
//    }
//    @GetMapping("/getUsersr")
//    @PreAuthorize("hasAuthority('USER_ROLES')")
//    public String getAllUsersr(){
//        return "User Role";
//    }


    //********************************* PATIENT RELATED ENDPOINT ****************************
    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/registerPatient")
    public String registerPatient(@RequestBody Patient patient){
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        patientRepository.save(patient);
        return "Patient registered successfully";

    }

    @GetMapping("/patientEndpoint")
    @PreAuthorize("hasAuthority('PATIENT')")
    public String patientEndpoint(){
        return "This is Patient";
    }


    //********************************* DOCTOR RELATED ENDPOINT ****************************
    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/registerDoctor")
    public String registerDoctor(@RequestBody Doctor doctor){
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctorRepository.save(doctor);
        return "Doctor registered successfully";

    }

    @GetMapping("/doctorEndpoint")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public String doctorEndpoint(){
        return "This is doctor";
    }

    //********************************* ADMIN RELATED ENDPOINT ****************************
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;



    @PostMapping("/registerHospital")
    public String registerDoctor(@RequestBody Hospital hospital){
        hospital.setPassword(passwordEncoder.encode(hospital.getPassword()));
        User user = new User();
        user.setEmail(hospital.getEmail());
        user.setPassword(hospital.getPassword());
        user.setRole(hospital.getRole());

        adminRepository.save(hospital);
        userInfoRepository.save(user);
        return "Hospital registered successfully";

    }

    @GetMapping("/hospitalEndpoint")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String hospitalEndpoint(){
        return "This is hospital";
    }



}