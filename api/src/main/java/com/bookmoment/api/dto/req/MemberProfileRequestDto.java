package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Builder
public class MemberProfileRequestDto {
    private Long id;
    private String name;
    private String email;
    private String passwd;

    public MemberProfileRequestDto(Long id, String name, String email, String passwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwd = passwd;
    }

    public static MemberProfileRequestDto UserDto(Member member) {
        return new MemberProfileRequestDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPasswd()
        );
    }
}
