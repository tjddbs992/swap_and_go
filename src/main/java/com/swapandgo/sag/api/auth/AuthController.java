package com.swapandgo.sag.api.auth;

import com.swapandgo.sag.dto.auth.LoginRequest;
import com.swapandgo.sag.dto.auth.LoginResponse;
import com.swapandgo.sag.dto.auth.SignupRequest;
import com.swapandgo.sag.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request){
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestHeader("Authorization") String refreshToken){
        String token = refreshToken.startsWith("Bearer ") ? refreshToken.substring(7) : refreshToken;

        System.out.println("Extracted token = [" + token + "]");

        LoginResponse response = authService.renewAccessToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        //클라이언트 측에서 토큰 삭제
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
