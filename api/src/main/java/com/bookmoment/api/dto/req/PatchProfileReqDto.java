package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Profile;
import com.bookmoment.api.entity.Music;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PatchProfileReqDto {
    @NotBlank(message = "토큰은 필수 입력 값입니다.")
    private String token;

    private String profileImage;
    private String name;
    private String coverColor;
    private String quoteTitle;
    private String quoteText;
    private Music music;

    public PatchProfileReqDto(String token, String profileImage, String name, String coverColor, String quoteTitle, String quoteText, Music music) {
        this.token = token;
        this.profileImage = profileImage;
        this.name = name;
        this.coverColor = coverColor;
        this.quoteTitle = quoteTitle;
        this.quoteText = quoteText;
        this.music = music;
    }

    public Profile toEntity() {
        return Profile.builder()
                .token(this.token)
                .profileImage(this.profileImage)
                .name(this.name)
                .coverColor(this.coverColor)
                .quoteTitle(this.quoteTitle)
                .quoteText(this.quoteText)
                .music(this.music)
                .build();
    }
}
