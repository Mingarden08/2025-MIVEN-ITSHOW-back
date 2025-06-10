package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Member;
import com.bookmoment.api.entity.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchProfileReqDto {

    @NotBlank(message = "이름은 필수 입력값입니다.")   
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profileImage;
    private String coverColor;
    private MusicReqDto music;
    private QuoteReqDto quote;

    /**
     * Profile Create
     * @param member
     * @return
     */
    public Profile toEntity(Member member) {
        return Profile.builder()
                .img(this.profileImage)
                .coverColor(this.coverColor)
                .music(this.music.toEntity())
                .quote(this.quote.toEntity())
                .member(member)
                .name(this.name)
                .quoteCount(0)
                .build();
    }
}
