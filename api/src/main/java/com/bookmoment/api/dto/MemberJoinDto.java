package com.bookmoment.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
public class MemberJoinDto {

    @Comment("회원 아이디")
    private String memberId;

    @Comment("회원명")
    private String name;

    private String email;

    private String password;

    private String addr;
}
