package com.bookmoment.api.dto.res;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor      // 기본 생성자
@AllArgsConstructor     // 모든 필드를 초기화하는 생성자
@Builder                // 빌더 패턴 적용
public class GalleryRes {
    private Long bookId;
    private String title;
    private String cover;
    private String writer;
    private String regTime;
}
