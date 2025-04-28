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

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^01[0-9]{9}$", message = "올바른 전화번호 형식(010xxxxxxxx)을 입력해주세요.")
    private String tel;

    //private Boolean isLock = false;      // 계정 잠금 여부 (기본값 false)
    //private Boolean isWithdraw = false;  // 탈퇴 여부 (기본값 false)

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .passwd(this.passwd)
                .tel(this.tel)
                .isLock(false)
                .isWithdraw(false)
                .build();
    }
}
