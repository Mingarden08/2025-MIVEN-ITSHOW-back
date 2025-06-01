package com.bookmoment.api.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TBL_COMMENT")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cmtText")
    @Schema(description = "댓글 내용")
    private String text;

    @OneToMany(mappedBy = "commentInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LikeIt> likeList;

    @ManyToOne
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    private Gallery galleryInfo;

    public Comment() {}
}
