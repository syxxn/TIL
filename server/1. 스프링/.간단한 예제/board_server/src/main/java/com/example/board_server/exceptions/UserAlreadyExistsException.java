package com.example.board_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "User Already Exists")
public class UserAlreadyExistsException extends RuntimeException{
}
