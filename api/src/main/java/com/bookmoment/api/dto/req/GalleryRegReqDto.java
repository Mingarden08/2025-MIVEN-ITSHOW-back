package com.bookmoment.api.dto.req;


import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.util.DateUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotBlank(message = "책 제목은 필수 입력 값입니다.")
    @Size(max = 255, message = "책 제목은 255자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "책 커버는 필수 입력 값입니다.")
    private String cover;

    @NotBlank(message = "isbn은 필수 입력 값입니다.")
    private String isbn;

    @NotBlank(message = "출판 일수는 필수 입력 값입니다.")
    private String publicDate;

    @NotBlank(message = "읽은 기간은 필수 입력 값입니다.")
    private String period;

    @NotBlank(message = "별점 필수 입력 값입니다.")
    @Size(max = 5, message = "별점은 1~5 사이로 입력해주세요.")
    private int rating;

    @NotBlank(message = "리뷰는 필수 입력 값입니다.")
    @Size(max = 255, message = "리뷰는 255자 이하로 입력해주세요.")
    private String review;

    @NotBlank(message = "인용구는 필수 입력 값입니다.")
    private String quote;

    @NotBlank(message = "페이지는 필수 입력 값입니다.")
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
