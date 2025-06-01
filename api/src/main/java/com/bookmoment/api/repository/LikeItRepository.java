package com.bookmoment.api.repository;

import com.bookmoment.api.entity.LikeIt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeItRepository extends JpaRepository<LikeIt, Long> {
}
