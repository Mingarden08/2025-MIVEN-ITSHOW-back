package com.bookmoment.api.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRes {
    private Long id;
    private String text;
    private String writer;
    private int likeCount;
}
