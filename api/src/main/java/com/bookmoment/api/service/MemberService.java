package com.bookmoment.api.service;

import com.bookmoment.api.constant.Role;
import com.bookmoment.api.dto.req.MemberProfileRequestDto;
import com.bookmoment.api.dto.req.MemberSignupRequestDto;
import com.bookmoment.api.dto.res.MemberInfoRes;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    //add to repository static variables;
    @Autowired
    MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원정보 조회
     * @param email
     * @return
     */
    public MemberInfoRes findByUserId(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        return memberOptional
                .map(member -> MemberInfoRes.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .name(member.getName())
                        .tel(member.getTel())
                        .isLock(member.isLock())
                        .isWithdraw(member.isWithdraw())
                        .role(member.getRole().getCode())
                        .regTime(member.getRegTime())
                        .build())
                .orElseThrow(() -> new NoSuchElementException("해당 이메일의 사용자를 찾을 수 없습니다."));
    }


    /**
     * 회원가입
     */
    public Member signup(MemberSignupRequestDto dto) {

        Optional<Member> memberOptional = memberRepository.findByEmail(dto.getEmail());
        if (memberOptional.isEmpty()) {

            //비밀번호 암호화.
            String encodedPassword = passwordEncoder.encode(dto.getPasswd());
            dto.setPasswd(encodedPassword);

            Member member = dto.toEntity();
            memberRepository.save(member);
            return dto.toEntity();
        } else {
            Member member = memberOptional.get();
            Role role = member.getRole();
            log.info(" role : {}", role.getCode() + "/" + role.getName());
            return member;
        }
        //이메일 중복체크, 전화번호 중복체크
    }

    public Optional<MemberProfileRequestDto> profile(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberProfileRequestDto::UserDto);
    }

    /**
     * 로그인
     */

    /**
     * 회원정보 수정
     */

    /**
     * 회원 탈퇴
     */
}
