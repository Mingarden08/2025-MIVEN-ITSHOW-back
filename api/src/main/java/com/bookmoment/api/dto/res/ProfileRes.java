package com.bookmoment.api.dto.res;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRes {

    private String profileImg;
    private String name;
    private String coverColor;
    private int quoteCount;
    private QuoteRes quote;
    private MusicRes music;

}