package com.bookmoment.api.entity;

import com.bookmoment.api.dto.req.MusicReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_MUSIC")
@Getter
@Setter
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
    @JoinColumn(name = "profile_id", referencedColumnName = "id", unique = true, nullable = false)
    private Profile profileInfo;

    public Music() {

    }

    /**
     * Entity 업데이트
     * @param dto
     */
    public void updateFrom(MusicReqDto dto) {
        this.song = dto.getSong();
        this.artist = dto.getArtist();
        this.setMusicId(dto.getMusicId());
    }
}
