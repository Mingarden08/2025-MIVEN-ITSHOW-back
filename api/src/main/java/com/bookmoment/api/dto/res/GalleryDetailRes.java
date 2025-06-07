package com.bookmoment.api.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryDetailRes {
    private Long id;
    private String title;
    private String cover;
    private String isbn;
    private String publicDate;
    private int pages;
    private String period;
    private String writer;
    private int rating;
    private String reviewText;
    private String quote;
    private int likeCount;
    private String regTime;
    private CommentListRes comments;
}
