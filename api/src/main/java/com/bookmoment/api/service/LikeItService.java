package com.bookmoment.api.service;

import com.bookmoment.api.dto.req.LikeItReqDto;
import com.bookmoment.api.dto.res.LikeItList;
import com.bookmoment.api.entity.Comment;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.LikeIt;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.CommentRepository;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.repository.LikeItRepository;
import com.bookmoment.api.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.ParserInterpreter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LikeItService {

    private final LikeItRepository likeItRepository;

    private final GalleryRepository galleryRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    /**
     * 갤러리 좋아요 수
     * @param Id
     * @return
     */
    public int getLikeItListOfGallery(Long Id) {
        //List<LikeIt> likeItList = likeItRepository.findByGalleryId(galleryId);
        //return likeItList.size();
        long count = likeItRepository.countByGalleryId(Id);
        return (int)count;
    }

    /**
     * 좋아요, 좋아요 취소
     * @param email
     * @param dto
     * @return
     */
    public int getLikeCount(String email, LikeItReqDto dto) {
        String flag = dto.getFlag();
        Long Id = dto.getObjectId();
        LikeItList res;

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        if ("R".equals(flag) || "r".equals(flag)) {
            Optional<LikeIt> existingLike = likeItRepository.findByLikedBy_IdAndGallery_Id(member.getId(), Id);

            if (existingLike.isPresent()) {
                likeItRepository.delete(existingLike.get());
            } else {
                Gallery gallery = galleryRepository.findByIdAndMemberId(member.getId(), Id);
                LikeIt newLike = LikeIt.builder()
                        .flag("R")
                        .likedBy(member)
                        .gallery(gallery)
                        .build();
                likeItRepository.save(newLike);
            }
            int likes = (int)likeItRepository.countByGalleryId(Id);
            return likes;
        }
        else if ("C".equals(flag) || "c".equals(flag)) {
            Optional<LikeIt> existingLike = likeItRepository.findByLikedBy_IdAndComment_Id(member.getId(), Id);

            if (existingLike.isPresent()) {
                likeItRepository.delete(existingLike.get());
            } else {
                Comment comment = commentRepository.findByIdAndWriter_Id(member.getId(), Id);
                LikeIt newLike = LikeIt.builder()
                        .flag("R")
                        .likedBy(member)
                        .comment(comment)
                        .build();
                likeItRepository.save(newLike);
            }
            int likes = (int)likeItRepository.countByCommentId(Id);
            return likes;
        } else {
            throw new IllegalArgumentException("Invalid flag: " + flag);
        }
    }
}
