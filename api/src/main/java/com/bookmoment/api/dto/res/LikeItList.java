package com.bookmoment.api.dto.res;

import com.bookmoment.api.entity.LikeIt;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikeItList {
    private int likes;
}
