package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Music;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MusicReqDto {

    private String musicId;
    private String song;
    private String artist;

    public Music toEntity() {
        return Music.builder()
                .musicId(this.musicId)
                .song(this.song)
                .artist(this.artist)
                .build();
    }

    public Music updateEntity(Music music) {
        music.setMusicId(this.musicId);
        music.setSong(this.song);
        music.setArtist(this.artist);
        return music;
    }
}
