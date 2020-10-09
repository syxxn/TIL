package com.example.board_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Invalid Token Exception")
public class InvalidTokenException extends RuntimeException{
}
