package com.bookmoment.api.entity;

import com.bookmoment.api.dto.req.QuoteReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_QUOTE")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Quote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Schema(description = "상태 메세지 제목")
    private String title;

    @Column(name = "text")
    @Schema(description = "상태 메세지 본문")
    private String text;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", unique = true, nullable = false)
    private Profile profileInfo;

    public Quote() {

    }

    /**
     * Entity Update
     * @param quote
     */
    public void updateFrom(QuoteReqDto quote) {
        this.title = quote.getTitle();
        this.text = quote.getText();
    }

}
