package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Quote;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteReqDto {

    private String title;
    private String text;

    public Quote toEntity() {
        return Quote.builder()
                .title(title)
                .text(text)
                .build();
    }
}
