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
    private String isbn;
    private String date;
    private int pages;
    private String period;
    private String writer;
    private int rating;
    private String reviewText;
    private String quote;
    private Long like;
    private String quoteDate;

    private CommentList comments; //댓글리스트
    private LikeItList likeItList; //좋아료 리스트
}
