package com.bookmoment.api.repository;

import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    /**
     * 내 갤러리 목록 검색
     * @param memberId, keyword
     * @return
     */
    ArrayList<Gallery> findByMemberInfo_idAndTitleContaining(Long memberId, String keyword);

    /**
     * 내가 작성한 갤러리 목록 조회
     * @param memberId
     * @return
     */
    ArrayList<Gallery> findByMemberInfo_id(Long memberId);

    /**
     * 내가 작성하 갤러리 상세 정보 조회
     * @param id
     * @param memberId
     * @return
     */
    Optional<Gallery> findByIdAndMemberInfo_id(Long id, Long memberId);

}
