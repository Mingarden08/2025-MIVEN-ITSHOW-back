package com.bookmoment.api.dto.res;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor      // 기본 생성자
@AllArgsConstructor     // 모든 필드를 초기화하는 생성자
@Builder
public class UserInfoDetailRes {
    private String addr;
}
