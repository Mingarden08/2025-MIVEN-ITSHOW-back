package com.bookmoment.api.service;

import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.repository.GalleryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;

    /**
     * 내가 작성한 갤러리 목록 조회
     * @return
     */
    public List<Gallery> findByAllMyGallery() {
        return null;
    }

    /**
     * 선택한 갤러리 상세 조회
     * @param id
     * @return
     */


}
