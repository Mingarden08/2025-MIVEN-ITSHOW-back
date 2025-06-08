package com.bookmoment.api.service;

import com.bookmoment.api.entity.LikeIt;
import com.bookmoment.api.repository.LikeItRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.ParserInterpreter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LikeItService {

    private final LikeItRepository likeItRepository;

    /**
     * 갤러리 좋아요 수
     * @param galleryId
     * @return
     */
    public int getLikeItListOfGallery(Long galleryId) {
        //List<LikeIt> likeItList = likeItRepository.findByGalleryId(galleryId);
        //return likeItList.size();
        long count = likeItRepository.countByGalleryId(galleryId);
        return (int)count;
    }
}
