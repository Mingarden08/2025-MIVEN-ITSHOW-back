package com.bookmoment.api.repository;

import com.bookmoment.api.entity.LikeIt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeItRepository extends JpaRepository<LikeIt, Long> {

    /**
     * 갤러리 좋아요 목록 조회
     * @param galleryId
     * @return
     */
    List<LikeIt> findByGalleryId(Long galleryId);

    /**
     * 갤러리 좋아요 수
     * @param galleryId
     * @return
     */
    public long countByGalleryId(Long galleryId);
}
