package com.codelad.logoinregister.services.implementations;

import com.codelad.logoinregister.dao.JwtAuthenticationResponse;
import com.codelad.logoinregister.dao.SigninRequest;
import com.codelad.logoinregister.dao.SignupRequest;
import com.codelad.logoinregister.entities.Role;
import com.codelad.logoinregister.entities.UserEntity;
import com.codelad.logoinregister.repositories.UserRepository;
import com.codelad.logoinregister.services.AuthenticationService;
import com.codelad.logoinregister.services.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationImplementation implements AuthenticationService {
    @Autowired @NonNull
    UserRepository userRepository;
    @Autowired @NonNull
    PasswordEncoder passwordEncoder;
    @Autowired @NonNull
    JwtService jwtService;
    @Autowired @NonNull
    AuthenticationManager authenticationManager;


    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        UserEntity user = UserEntity.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(request.getRole().equalsIgnoreCase("user") ? Role.USER : Role.ADMIN).build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        String jwtToken = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwtToken).build();
    }
}
