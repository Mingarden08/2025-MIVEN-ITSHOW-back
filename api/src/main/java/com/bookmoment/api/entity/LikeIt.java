package com.bookmoment.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_LIKEIT")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class LikeIt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "flag", length = 1, nullable = false)
    @Schema(description = "구분자(R or C)")
    private String flag;

//    @Column(name = "reviewId")
//    @Schema(description = "리뷰 고유 식별자")
//    private Long reviewId;
//
//    @Column(name = "commentId")
//    @Schema(description = "댓글 고유 식별자")
//    private Long commentId;

    @Schema(description = "누가 좋아요를 했는지")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member likedBy;

    @Schema(description = "댓글 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Schema(description = "갤러리 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Gallery gallery;

    public LikeIt() {

    }
}
