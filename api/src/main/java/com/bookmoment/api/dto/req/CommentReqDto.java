package com.bookmoment.api.dto.req;

import com.bookmoment.api.entity.Comment;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReqDto {

    @NotBlank(message = "필수 입력 값입니다.")
    @Size(max = 255, message = "댓글은 255자 이하로 입력해주세요.")
    private String text;

    public Comment toEntity(Member member, Gallery gallery) {
        return Comment.builder()
                .text(this.text)
                .writer(member)
                .gallery(gallery)
                .build();
    }
}
