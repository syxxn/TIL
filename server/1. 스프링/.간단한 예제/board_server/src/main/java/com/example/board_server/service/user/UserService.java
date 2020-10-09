package com.example.board_server.service.user;

import com.example.board_server.payload.request.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
}
