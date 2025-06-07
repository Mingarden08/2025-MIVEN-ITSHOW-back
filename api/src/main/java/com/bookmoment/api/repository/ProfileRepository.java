package com.bookmoment.api.repository;

import com.bookmoment.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    /**
     * 맴버 고유 아이디로 프로필 조회
     * @param id
     * @return
     */
    Optional<Profile> findByMemberId(Long id);
}
