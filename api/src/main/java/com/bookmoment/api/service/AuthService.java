package com.bookmoment.api.service;

import com.bookmoment.api.dto.req.LoginReq;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    // JWT SECRET KEY (32바이트 이상)
    private static final String SECRET_KEY = "3f1mA+P8Bfhq2B4h2QhL0K3aR9S5tVbA8JZx+8YQ+qU=";

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 JWT 서명 키 생성
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String login(LoginReq req) {
        Optional<Member> memberOptional = memberRepository.findByEmail(req.getEmail());
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Member member = memberOptional.get();
        //비밀번호 검증
        if (!passwordEncoder.matches(req.getPasswd(), member.getPasswd())) {
            throw new RuntimeException("Invalid Password");
        }


        return Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
