package com.example.board_server.service.board;

import com.example.board_server.payload.request.BoardRequest;

public interface BoardService {
    void writeBoard(String token, BoardRequest boardRequest); //post는 거의 다 void

}
