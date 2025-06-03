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

    @Column(name = "bookId")
    @Schema(description = "책 식별 아이디")
    private Long bookId;

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
    private LocalDateTime date;

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

    @Column(name = "quote")
    @Schema(description = "독후감")
    private String quote;

    @Column(name = "quoteDate")
    @Schema(description = "등록날짜")
    private LocalDateTime quoteDate;

    @ManyToOne
    //@MapsId("idMember")
    @JoinColumn(name = "member_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_member_id"))
    private Member memberInfo;

    @OneToMany(mappedBy = "galleryInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LikeIt> likeList;

    @OneToMany(mappedBy = "galleryInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    public Gallery() {

    }
}
