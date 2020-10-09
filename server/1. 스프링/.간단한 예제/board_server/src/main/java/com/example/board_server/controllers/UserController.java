package com.example.board_server.controllers;

import com.example.board_server.payload.request.SignUpRequest;
import com.example.board_server.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController{
    private final UserService userService;

    @PostMapping("/user")
    public void signUp(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest);
    }
}
