package com.bookmoment.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.bookmoment.api.entity.Profile;

@Getter
@Setter
@Builder
public class GetProfileRes {
    private String dateTime;
    private String version;
    private int code;
    private String message;
    private boolean success;
    private Profile data;


    public GetProfileRes toEntity() {
        return GetProfileRes.builder()
                .dateTime(this.dateTime)
                .version(this.version)
                .code(this.code)
                .message(this.message)
                .success(this.success)
                .data(this.data)
                .build();
    }
}
