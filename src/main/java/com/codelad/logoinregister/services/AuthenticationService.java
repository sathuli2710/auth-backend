package com.codelad.logoinregister.services;

import com.codelad.logoinregister.dao.JwtAuthenticationResponse;
import com.codelad.logoinregister.dao.SigninRequest;
import com.codelad.logoinregister.dao.SignupRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}
