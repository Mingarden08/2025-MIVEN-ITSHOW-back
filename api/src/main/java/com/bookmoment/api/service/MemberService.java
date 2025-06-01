package com.bookmoment.api.service;

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
    private final MemberRepository memberRepository;

    private final GalleryService galleryService;

    private final ProfileService profileService;

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
                        .build())
                .orElseThrow(() -> new NoSuchElementException("해당 이메일의 사용자를 찾을 수 없습니다."));
    }


    /**
     * 회원가입
     */
    public boolean signup(MemberSignupRequestDto dto) {
        Optional<Member> memberOptional = memberRepository.findByEmail(dto.getEmail());
        //이메일 중복체크
        if (memberOptional.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPasswd());
        dto.setPasswd(encodedPassword);

        Member member = dto.toEntity();
        memberRepository.save(member);
        return true;
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
