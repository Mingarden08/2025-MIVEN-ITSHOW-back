package com.bookmoment.api.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeItReqDto {

    @NotBlank(message = "flag는 필수 입력 값입니다.")
    @Size(max = 1, message = "R, r or C, c 로 입력해주세요.")
    private String flag;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private Long ObjectId;

}
