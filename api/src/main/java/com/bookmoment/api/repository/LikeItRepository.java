package com.bookmoment.api.repository;

import com.bookmoment.api.entity.LikeIt;
import com.bookmoment.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
<<<<<<< HEAD
    long countByGalleryId(Long galleryId);

    long countByCommentId(Long commentId);


    /**
     * 누가 갤러리에 좋아요를 눌렀는지
     * @param memberId
     * @param galleryId
     * @return
     */
    Optional<LikeIt> findByLikedBy_IdAndGallery_Id(Long memberId, Long galleryId);

    Optional<LikeIt> findByLikedBy_IdAndComment_Id(Long memberId, Long commentId);
=======
    public long countByGalleryId(Long galleryId);

    Optional<LikeIt> findById(Long id);

    public long countByGalleryAndFlag(Long galleryId, String flag);
>>>>>>> master
}
