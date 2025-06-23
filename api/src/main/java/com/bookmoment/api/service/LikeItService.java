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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Locale;
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

    private final MemberService memberService;

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
        String flag = dto.getFlag().toUpperCase(Locale.ROOT);
        Long Id = dto.getObjectId();
        LikeItList res;

//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Member member = memberService.getMemberByEmail(email);
        log.info("member: {}", member.getName());

        if (flag.equals("R")) {
            //1. 갤러리 좋아요 할 경우, Id 로 갤러리를 찾는다.
            Optional<Gallery> galleryOptional = galleryRepository.findById(Id);
            if (galleryOptional.isEmpty())
                return -10;

            //해당 갤럴리에 로그인한 사용자가 좋아요 했는지 찾는다.
            Gallery gallery = galleryOptional.get();
            Optional<LikeIt> likeItOptional = likeItRepository.findByLikedBy_IdAndGallery_Id(member.getId(), gallery.getId());
            if (likeItOptional.isEmpty()) {
                //좋아요 한적이 없다면 추가
                LikeIt likeIt = LikeIt.builder()
                        .flag(flag)
                        .likedBy(member)
                        .gallery(galleryOptional.get())
                        .build();
                likeItRepository.save(likeIt);
            } else {
                //좋아요 한적이 있다면 취소로 간주, 삭제
                likeItRepository.delete(likeItOptional.get());
            }

            //좋아요 수 리턴
            return (int) likeItRepository.countByGalleryId(gallery.getId());
        } else {
            //댓글 좋아요 일 경우
            //1. 댓글을 찾는다.
            Optional<Comment> commentOptional = commentRepository.findById(Id);
            if (commentOptional.isEmpty())
                return -10;

            //해당 댓글에 로그인 한 사용자가 좋아요 했는지 찾는다.
            Comment comment = commentOptional.get();
            Optional<LikeIt> likeItOptional = likeItRepository.findByLikedBy_IdAndComment_Id(member.getId(), comment.getId());
            if (likeItOptional.isEmpty()) {
                //좋아요 한 적이 없다면 추가한다.
                LikeIt likeIt = LikeIt.builder()
                        .flag(flag)
                        .likedBy(member)
                        .comment(comment)
                        .build();
                likeItRepository.save(likeIt);
            } else {
                //이미 좋아요 했다면 취소로 간주한다.
                likeItRepository.delete(likeItOptional.get());
            }

            //좋아요 수 리턴
            return Math.toIntExact(likeItRepository.countByCommentId(comment.getId()));
        }


//        if ("R".equals(flag) || "r".equals(flag)) {
//            Optional<LikeIt> existingLike = likeItRepository.findByLikedBy_IdAndGallery_Id(member.getId(), Id);
//            if (existingLike.isPresent()) {
//                likeItRepository.delete(existingLike.get());
//            } else if (existingLike.isEmpty()) {
//                return -10;
//            } else {
//                Gallery gallery = galleryRepository.findByIdAndMemberId(member.getId(), Id);
//                LikeIt newLike = LikeIt.builder()
//                        .flag("R")
//                        .likedBy(member)
//                        .gallery(gallery)
//                        .build();
//                likeItRepository.save(newLike);
//            }
//            int likes = (int)likeItRepository.countByGalleryId(Id);
//            return likes;
//        }
//        else if ("C".equals(flag) || "c".equals(flag)) {
//            Optional<LikeIt> existingLike = likeItRepository.findByLikedBy_IdAndComment_Id(member.getId(), Id);
//
//            if (existingLike.isPresent()) {
//                likeItRepository.delete(existingLike.get());
//            } else {
//                Comment comment = commentRepository.findByIdAndWriter_Id(member.getId(), Id);
//                LikeIt newLike = LikeIt.builder()
//                        .flag("R")
//                        .likedBy(member)
//                        .comment(comment)
//                        .build();
//                likeItRepository.save(newLike);
//            }
//            int likes = (int)likeItRepository.countByCommentId(Id);
//            return likes;
//        } else {
//            throw new IllegalArgumentException("Invalid flag: " + flag);
//        }
    }
}
