package com.bookmoment.api.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LoginResultRes {

    @Schema(description = "로그인 인증 토큰")
    private String token;

}
