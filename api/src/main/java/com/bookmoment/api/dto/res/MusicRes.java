package com.bookmoment.api.dto.res;

import com.bookmoment.api.entity.Music;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicRes {

    private String musicId;
    private String song;
    private String artist;

    public MusicRes toDto(Music music) {
        return MusicRes.builder()
                .musicId(music.getMusicId())
                .song(music.getSong())
                .artist(music.getArtist())
                .build();
    }

    public MusicRes(Music music) {
        if (music == null) {
            this.musicId = "";
            this.artist = "";
            this.song = "";
            return;
        }
        this.musicId = music.getMusicId();
        this.artist = music.getArtist();
        this.song = music.getSong();
    }
}
