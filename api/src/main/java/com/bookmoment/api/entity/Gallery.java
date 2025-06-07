package com.bookmoment.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TBL_GALLERY")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Gallery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Schema(description = "책 제목")
    private String title;

    @Column(name = "cover")
    @Schema(description = "책 표지")
    private String cover;

    @Column(name = "isbn")
    @Schema(description = "책 고유 아이디")
    private String isbn;

    @Column(name = "date")
    @Schema(description = "책 발행 일자")
    private LocalDateTime publicDate;

    @Column(name = "pages")
    @Schema(description = "책 장수")
    private int pages;

    @Column(name = "period")
    @Schema(description = "책 읽은 기간")
    private String period;

    @Column(name = "rating")
    @Schema(description = "평점")
    private int rating;

    @Column(name = "reviewText")
    @Schema(description = "한 줄 소감")
    private String reviewText;

    @Column(name = "quote", columnDefinition = "TEXT")
    @Schema(description = "독후감")
    private String quote;

    @ManyToOne
    @JoinColumn(name = "member_id",
            foreignKey = @ForeignKey(name = "FK_member_id"),
            nullable = false)
    private Member member;

    public Gallery() {

    }
}
