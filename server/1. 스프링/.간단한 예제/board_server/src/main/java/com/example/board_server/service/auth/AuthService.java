package com.example.board_server.service.auth;

import com.example.board_server.payload.request.SignInRequest;
import com.example.board_server.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest signInRequest);
}
