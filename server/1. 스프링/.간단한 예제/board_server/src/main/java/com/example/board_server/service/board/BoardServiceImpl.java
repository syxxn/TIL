package com.example.board_server.service.board;

import com.example.board_server.Utils.JwtUtil;
import com.example.board_server.entities.board.Board;
import com.example.board_server.entities.board.BoardRepository;
import com.example.board_server.entities.user.User;
import com.example.board_server.entities.user.UserRepository;
import com.example.board_server.exceptions.UserNotFoundException;
import com.example.board_server.payload.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository; //필드는 무조건 private
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository; // DI 의존성 주입 //DI dependency 인젝션 //@RequiredArgsConstructor 같은 기능


    @Override
    public void writeBoard(String token, BoardRequest boardRequest) {
        User user=userRepository.findById(jwtUtil.parseToken(token)).orElseThrow(UserNotFoundException::new); //요청 보낸 유저 객체를 불러옴
        boardRepository.save(
                Board.builder()
                        .name(user.getName())
                        .title(boardRequest.getTitle())
                        .content(boardRequest.getContent())
                        .userId(user.getId()) //userId는 user갖고있음
                        .createdAt(LocalDateTime.now()) //현재 시간이 들어감
                .build()
        );


    }


}
