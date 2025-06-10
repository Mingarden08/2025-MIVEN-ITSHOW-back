package com.bookmoment.api.dto.res;

import com.bookmoment.api.entity.Quote;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRes {

    private String title;
    private String text;

    public QuoteRes toDto(Quote quote) {
        return QuoteRes.builder()
                .title(quote.getTitle())
                .text(quote.getText())
                .build();
    }

    public QuoteRes(Quote quote) {
        if (quote == null) {
            this.title = "";
            this.text = "";
            return;
        }
        this.title = quote.getTitle();
        this.text = quote.getText();
    }

}
