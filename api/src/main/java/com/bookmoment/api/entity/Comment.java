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

    @ManyToOne(fetch = FetchType.LAZY)
    private Gallery gallery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LikeIt> likeList;

    public Comment() {}
}
