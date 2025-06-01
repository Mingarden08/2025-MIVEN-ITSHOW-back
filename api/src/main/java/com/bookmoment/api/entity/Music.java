package com.bookmoment.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_MUSIC")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Music extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "musicId")
    @Schema(description = "노래 고유 키")
    private String musicId;

    @Column(name = "song")
    @Schema(description = "노래 제목")
    private String song;

    @Column(name = "artist")
    @Schema(description = "가수")
    private String artist;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", unique = true, nullable = true)
    private Profile profileInfo;

    public Music() {

    }
}
