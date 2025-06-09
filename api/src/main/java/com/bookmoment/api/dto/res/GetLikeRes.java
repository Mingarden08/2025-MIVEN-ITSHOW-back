package com.bookmoment.api.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLikeRes {
    private String message;
    private LikeRes data;
}
