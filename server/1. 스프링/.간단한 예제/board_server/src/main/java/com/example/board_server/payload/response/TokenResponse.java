package com.example.board_server.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse(Integer id) {
    }
}
