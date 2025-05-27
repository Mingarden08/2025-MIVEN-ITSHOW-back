package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MemberSignupRequestDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 25, message = "이름은 2자 이상, 25자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 50, message = "비밀번호는 최소 8자 이상, 최대 50자 이하로 입력해주세요.")
    private String passwd;


    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .passwd(this.passwd)
                .build();
    }
}
