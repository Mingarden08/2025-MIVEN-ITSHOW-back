package com.bookmoment.api.controller;

import com.bookmoment.api.dto.req.LoginReq;
import com.bookmoment.api.dto.res.DataResponse;
import com.bookmoment.api.dto.res.LoginResultRes;
import com.bookmoment.api.dto.res.ResponseCode;
import com.bookmoment.api.service.AuthService;
import com.bookmoment.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResponse<LoginResultRes>> login(@RequestBody LoginReq req) {
        String token = authService.login(req);
        LoginResultRes res = new LoginResultRes();
        res.setToken(token);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }
}
