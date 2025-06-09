package com.bookmoment.api.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLikeReqDto {
    private String flag;
    private String reviewId;
    private String commentId;

}
