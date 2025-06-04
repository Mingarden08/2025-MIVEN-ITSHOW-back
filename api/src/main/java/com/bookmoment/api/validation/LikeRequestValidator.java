package com.bookmoment.api.validation;

import com.bookmoment.api.dto.req.GetLikeReqDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class LikeRequestValidator implements ConstraintValidator<ValidLikeRequest, GetLikeReqDto> {

    @Override
    public boolean isValid(GetLikeReqDto dto, ConstraintValidatorContext context) {
        String flag = dto.getFlag();
        String reviewId = dto.getReviewId();
        String commentId = dto.getCommentId();

        if ("R".equalsIgnoreCase(flag)) {
            return StringUtils.hasText(reviewId);
        } else if ("C".equalsIgnoreCase(flag)) {
            return StringUtils.hasText(commentId);
        }

        // flag가 R이나 C가 아니면 invalid로 간주
        return false;
    }
}
