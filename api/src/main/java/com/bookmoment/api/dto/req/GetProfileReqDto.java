package com.bookmoment.api.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProfileReqDto {
    private String token;
}
