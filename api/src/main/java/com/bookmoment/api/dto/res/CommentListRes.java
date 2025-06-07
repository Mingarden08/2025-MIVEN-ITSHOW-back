package com.bookmoment.api.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListRes {
    private List<CommentRes> comments;
}
