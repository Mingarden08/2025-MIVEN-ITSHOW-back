package com.bookmoment.api.dto.res;

import com.bookmoment.api.constant.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor      // 기본 생성자
@AllArgsConstructor     // 모든 필드를 초기화하는 생성자
@Builder                // 빌더 패턴 적용
public class MemberInfoRes {
    private Long id;
    private String name;
    private String email;
    private String tel;
    private boolean isLock;
    private boolean isWithdraw;
    private String role;
    private LocalDateTime regTime;
    private UserInfoDetailRes detailRes;
}
