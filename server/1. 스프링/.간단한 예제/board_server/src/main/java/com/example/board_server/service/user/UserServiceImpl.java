package com.example.board_server.service.user;

import com.example.board_server.entities.user.User;
import com.example.board_server.entities.user.UserRepository;
import com.example.board_server.exceptions.UserAlreadyExistsException;
import com.example.board_server.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    public final UserRepository userRepository;

    @Override
    public void signUp(SignUpRequest signUpRequest){
        userRepository.findByEmail(signUpRequest.getEmail()).ifPresent(user->{
                 throw new UserAlreadyExistsException();
        });
        userRepository.save(
                User.builder()
                        .email(signUpRequest.getEmail())
                        .password(signUpRequest.getPassword())
                .build()
        );
    }
}
