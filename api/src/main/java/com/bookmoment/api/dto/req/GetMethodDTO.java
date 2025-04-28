package com.bookmoment.api.dto.req;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetMethodDTO {
    private String name;
    private String email;
    private String pwd;
}
