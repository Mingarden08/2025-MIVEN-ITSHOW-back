package com.bookmoment.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_LIKEIT")
@Getter
@Setter
@ToString
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

    @Column(name = "reviewId")
    @Schema(description = "리뷰 고유 식별자")
    private Long reviewId;

    @Column(name = "commentId")
    @Schema(description = "댓글 고유 식별자")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment commentInfo;

    @ManyToOne
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    private Gallery galleryInfo;

    public LikeIt() {

    }
}
