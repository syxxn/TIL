package com.example.board_server.payload.request;

import lombok.Getter;

@Getter
public class BoardRequest {
    private String title;
    private String content;
}
