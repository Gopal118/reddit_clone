package com.redditCloneServer.redditCloneServer.controller;

import com.redditCloneServer.redditCloneServer.dto.RegisterRequest;
import com.redditCloneServer.redditCloneServer.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        try {
            return new ResponseEntity<>("Hello World", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        try {
            authService.signup(registerRequest);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    return new ResponseEntity<>("User Register successful", HttpStatus.OK);
    }


    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        try{
        authService.verifyAccount(token);
    }catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
        return new ResponseEntity<>("Account activated Successfully",HttpStatus.OK);
    }




}
