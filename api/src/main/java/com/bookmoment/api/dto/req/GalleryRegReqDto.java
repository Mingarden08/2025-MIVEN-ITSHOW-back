package com.bookmoment.api.dto.req;


import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.util.DateUtils;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 글 등록, 수정 사용
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryRegReqDto {
    private String title;
    private String cover;
    private String isbn;
    private String publicDate;
    private String period;
    private int rating;
    private String review;
    private String quote;
    private int pages;

    public Gallery toEntity(Member writer) {
        LocalDate publicDate = DateUtils.toLocalDate(this.publicDate, DateUtils.FORMAT_DATE_BAR);
        return Gallery.builder()
                .title(this.title)
                .cover(this.cover)
                .isbn(this.isbn)
                .publicDate(publicDate.atStartOfDay())
                .period(this.period)
                .rating(this.rating)
                .reviewText(this.review)
                .quote(this.quote)
                .pages(this.pages)
                .member(writer)
                .build();
    }
}
