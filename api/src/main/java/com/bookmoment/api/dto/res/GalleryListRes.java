package com.bookmoment.api.dto.res;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GalleryListRes {
    public List<GalleryRes> books = new ArrayList<>();
}
