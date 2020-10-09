package com.example.board_server.service.auth;

import com.example.board_server.Utils.JwtUtil;
import com.example.board_server.entities.user.User;
import com.example.board_server.entities.user.UserRepository;
import com.example.board_server.exceptions.UserNotFoundException;
import com.example.board_server.payload.request.SignInRequest;
import com.example.board_server.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

   public final UserRepository userRepository;

   @Override
    public TokenResponse signIn(SignInRequest signInRequest){
       String email=signInRequest.getEmail();
       String password=signInRequest.getPassword();

       User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

       if(!user.getPassword().equals(password))throw new UserNotFoundException();
       return tokenResponse(user.getId());
   }
   private TokenResponse tokenResponse(Integer id){
       return new TokenResponse(id);
   }
}
