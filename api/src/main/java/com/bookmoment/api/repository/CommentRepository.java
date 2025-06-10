package com.bookmoment.api.repository;

import com.bookmoment.api.entity.Comment;
import com.bookmoment.api.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    /**
     * 갤러리 댓글 목록
     * @param galleryId
     * @return
     */
    List<Comment> findByGalleryIdOrderByIdDesc(Long galleryId);

    Comment findByIdAndWriter_Id(Long id, Long memberId);

}
