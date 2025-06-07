package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Comment;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReqDto {
    private String text;

    public Comment toEntity(Member member, Gallery gallery) {
        return Comment.builder()
                .text(this.text)
                .writer(member)
                .gallery(gallery)
                .build();
    }
}
