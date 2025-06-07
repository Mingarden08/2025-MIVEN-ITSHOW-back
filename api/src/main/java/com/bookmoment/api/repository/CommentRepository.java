package com.bookmoment.api.repository;

import com.bookmoment.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 갤러리 댓글 목록
     * @param galleryId
     * @return
     */
    List<Comment> findByGalleryIdOrderByIdDesc(Long galleryId);
}
