package com.example.board_server.payload.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private int age;
}
