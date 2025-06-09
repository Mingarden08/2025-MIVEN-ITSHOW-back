package com.bookmoment.api.controller;

import com.bookmoment.api.dto.req.MemberProfileRequestDto;
import com.bookmoment.api.dto.req.MemberSignupRequestDto;
import com.bookmoment.api.dto.res.DataResponse;
import com.bookmoment.api.dto.res.MemberInfoRes;
import com.bookmoment.api.dto.res.ResponseCode;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.service.MemberService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<DataResponse<?>> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {
        try {
            boolean result = false;
            result = memberService.signup(requestDto);
            Map<String, Boolean> res = new HashMap<>();
            res.put("success", result);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.ALREADY_EXIST_USER_EMAIL, e.getMessage()));
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<DataResponse<MemberInfoRes>> profile(@Parameter(hidden = true) Authentication authentication,
                                                               HttpServletRequest request) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //또 다른 방법
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }

        String id = authentication.getName();
        MemberInfoRes res = memberService.findByUserId(id);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }

//    @PatchMapping("/{userEmail}")
//    public ResponseEntity<DataResponse<PatchProfileRes>> updateProfile(@PathVariable('userEmail') String userEmail, @ResponseBody PatchMemberProfileReqDto dto) {
//        return this.updateProfile(userEmail, dto)
//    }
//
//    @GetMapping()
//    public ResponseEntity<DataResponse<MemberInfoRes>> getProfile(@RequestBody GetProfileReqDto dto) {
//        return this.getProfileByToken(dto.getToken());
//    }
}
