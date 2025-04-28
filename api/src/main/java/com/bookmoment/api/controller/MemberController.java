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

@RestController
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    /**
     * 회원가입 처리 API
     * @param requestDto
     * @return
    {
    "name" : "민영후",
    "email" : "younghu.min@gmail.com",
    "passwd" : "asdfasdfasdfasdf",
    "tel" : "01083392852"
    }
     */
    @PostMapping("/signup")
    public ResponseEntity<DataResponse<?>> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {

        //TODO: - member service 처리
        Member member = memberService.signup(requestDto);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, member));
    }

// 아빠 주석
//    @GetMapping("/profile/{email}")
//    public ResponseEntity<MemberProfileRequestDto> profile(@PathVariable String email) {
//
//        log.info(">>> profile call");
//        return memberService.profile(email)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());

    /**
     * Authentication을 이용한 인증정보 추출 후 회원 정보 조회
     * @param authentication
     * @param request
     * @return
     */
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
}
