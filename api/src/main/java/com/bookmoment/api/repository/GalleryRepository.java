package com.bookmoment.api.repository;

import com.bookmoment.api.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    /**
     * 갤러리 전체 목록 가져오기(내림차순)
     * @return
     */
    List<Gallery> findAllByOrderByIdDesc();

    /**
     * 갤러리 목록 검색(내림차순)
     * @param keyword
     * @return
     */
    List<Gallery> findByTitleContainingOrderByIdDesc(String keyword);

    /**
     * 내가 작성한 겔러리 목록 카운드
     * @param memberId
     * @return
     */
    Long countByMemberId(Long memberId);

    /**
     * 내가 작성한 갤러리 목록 조회
     * @param memberId
     * @return
     */
    List<Gallery> findByMemberId(Long memberId);

    /**
     * 내가 작성하 갤러리 상세 정보 조회
     * @param id
     * @param memberId
     * @return
     */
    Gallery findByIdAndMemberId(Long id, Long memberId);

    /**
     * 내가 작성한
     * @param isbn
     * @param memberId
     * @return
     */
    Long findIdByIsbn(String isbn);
}
