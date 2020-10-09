package com.example.board_server.controllers;


import com.example.board_server.payload.request.BoardRequest;
import com.example.board_server.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {
    public final BoardService boardService;

    @PostMapping
    public void writeBoard(@RequestHeader("Authorization")String token,//Authorization 키 값, 밸류값이 token
                           @RequestBody BoardRequest boardRequest){
        boardService.writeBoard(token, boardRequest);
    }

}
