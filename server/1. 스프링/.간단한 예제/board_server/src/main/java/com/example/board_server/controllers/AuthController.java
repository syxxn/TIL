package com.example.board_server.controllers;


import com.example.board_server.payload.request.SignInRequest;
import com.example.board_server.payload.response.TokenResponse;
import com.example.board_server.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }

}
