package com.bookmoment.api.dto.req;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeItReqDto {

    private String flag;
    private Long ObjectId;

}
