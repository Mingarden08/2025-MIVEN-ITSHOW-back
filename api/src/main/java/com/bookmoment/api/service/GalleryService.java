package com.bookmoment.api.service;

import com.bookmoment.api.dto.req.CommentReqDto;
import com.bookmoment.api.dto.req.GalleryRegReqDto;
import com.bookmoment.api.dto.res.GalleryDetailRes;
import com.bookmoment.api.dto.res.GalleryListRes;
import com.bookmoment.api.dto.res.GalleryRes;
import com.bookmoment.api.entity.LikeIt;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.repository.LikeItRepository;
import com.bookmoment.api.repository.MemberRepository;
import com.bookmoment.api.repository.CommentRepository;
import com.bookmoment.api.util.DateUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bookmoment.api.dto.req.GetLikeReqDto;
import com.bookmoment.api.dto.res.GetLikeRes;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

//    public GalleryListRes findByAllMyGallery(String userId) {
//        GalleryListRes galleryListRes = new GalleryListRes();
//        Optional<Member> memberOptional = memberRepository.findByEmail(userId);
//
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            Long id = member.getId(); // member PK
//            List<Gallery> galleryList = galleryRepository.findByMemberId(id);
//            List<GalleryRes> galleryResList = galleryList.stream().map(gallery -> {
//                return GalleryRes.builder()
//                        .pages(gallery.getPages())
//                        .like(gallery.getLikeList().stream().count())
//                        .quote(gallery.getQuote())
//                        .isbn(gallery.getIsbn())
//                        .title(gallery.getTitle())
//                        .date(DateUtils.getLocalDateTimeString(gallery.getDate(), DateUtils.FORMAT_DATE_UNIT_BAR))
//                        .cover(gallery.getCover())
//                        .reviewText(gallery.getReviewText())
//                        .rating(gallery.getRating())
//                        .bookId(gallery.getBookId())
//                        .period(gallery.getPeriod())
//                        .writer(member.getName())
//                        .quoteDate(DateUtils.getLocalDateTimeString(gallery.getDate(), DateUtils.FORMAT_DATE_UNIT_BAR))
//                        .build();
//            }).collect(Collectors.toList());
//            galleryListRes.setBooks(galleryResList);
//        }
//        return galleryListRes;
//    }
  
    @Autowired
    LikeItService likeItService;

    @Autowired
    CommentService commentService;

    /**
     * 겔러리 등록하기
     * @param userId
     * @param reqDto
     * @return
     */
    public boolean galleryRegister(String userId, GalleryRegReqDto reqDto) {
        // 회원정보 구하기
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        Gallery gallery = reqDto.toEntity(member);
        galleryRepository.save(gallery);
        return true;
    }

    /**
     * 전체 갤러리 조회(키워드 검색)
     * @param userId
     * @param keyword
     * @return
     */
    public GalleryListRes galleryList(String userId, String keyword) {
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        GalleryListRes res = new GalleryListRes();

        List<Gallery> galleryList = (keyword == null) ? galleryRepository.findAllByOrderByIdDesc() : galleryRepository.findByTitleContainingOrderByIdDesc(keyword);
        List<GalleryRes> galleryResList = galleryList.stream().map(gallery -> {
            String regTime = DateUtils.getDateTimeString(gallery.getRegTime());
            return GalleryRes.builder()
                    .bookId(gallery.getId())
                    .title(gallery.getTitle())
                    .cover(gallery.getCover())
                    .writer(gallery.getMember().getName())
                    .regTime(regTime)
                    .build();
        }).collect(Collectors.toList());
        res.setBooks(galleryResList);
        return res;
    }

    /**
     * 갤러리 상세 조회
     * @param userId
     * @param galleryId
     * @return
     */
    public GalleryDetailRes galleryDetail(String userId, Long galleryId) {
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        GalleryDetailRes res = new GalleryDetailRes();
        Optional<Gallery> galleryOptional = galleryRepository.findById(galleryId);
        if (galleryOptional.isPresent()) {
            Gallery gallery = galleryOptional.get();
            String publicDate = DateUtils.getLocalDateTimeString(gallery.getPublicDate(), DateUtils.FORMAT_DATE_UNIT_BAR);
            String regTime = DateUtils.getDateTimeString(gallery.getRegTime());
            res = GalleryDetailRes.builder()
                    .id(gallery.getId())
                    .title(gallery.getTitle())
                    .cover(gallery.getCover())
                    .isbn(gallery.getIsbn())
                    .publicDate(publicDate)
                    .pages(gallery.getPages())
                    .period(gallery.getPeriod())
                    .writer(gallery.getMember().getName())
                    .rating(gallery.getRating())
                    .reviewText(gallery.getReviewText())
                    .quote(gallery.getQuote())
                    .likeCount(likeItService.getLikeItListOfGallery(gallery.getId()))
                    .regTime(regTime)
                    .comments(commentService.getComments(gallery.getId()))
                    .build();
            return res;
        } else {
            return null;
        }
    }

    public GetLikeRes getLikeCount(Long galleryId, GetLikeReqDto dto) {
        String flag = dto.getFlag();
        String userId = dto.getUserId();

        Gallery gallery = galleryRepository.findById(galleryId)
            .orElseThrow(() -> new IllegalArgumentException("Gallery not found"));

        Member member = memberRepository.findById(Long.parseLong(userId))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        long likeCount;

        if ("R".equals(flag)) {
            Optional<LikeIt> existingLike = likeItRepository.findByLikedByAndGallery(member, gallery);

            if (existingLike.isPresent()) {
                likeItRepository.delete(existingLike.get());
            } else {
                LikeIt newLike = LikeIt.builder()
                    .flag("R")
                    .likedBy(member)
                    .gallery(gallery)
                    .build();
                likeItRepository.save(newLike);
            }

            likeCount = likeItRepository.countByGalleryAndFlag(gallery, "R");
        }

        else if ("C".equals(flag)) {
            Long commentId = Long.parseLong(dto.getCommentId());

            Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

            Optional<LikeIt> existingLike = likeItRepository.findByLikedByAndComment(member, comment);

            if (existingLike.isPresent()) {
                likeItRepository.delete(existingLike.get());
            } else {
                LikeIt newLike = LikeIt.builder()
                    .flag("C")
                    .likedBy(member)
                    .comment(comment)
                    .gallery(gallery)
                    .build();
                likeItRepository.save(newLike);
            }

            likeCount = likeItRepository.countByComment(comment);
        } else {
            throw new IllegalArgumentException("Invalid flag: " + flag);
        }

        return GetLikeRes.build()
            .success(true)
            .likeCount(likeCount);
    }

    /**
     * 갤러리 수정 @Transactional이 있어야 데이터 수정이 됨
     * @param userId
     * @param galleryId
     * @param reqDto
     * @return
     */
    public boolean galleryUpdate(String userId, Long galleryId, GalleryRegReqDto reqDto) {
        Optional<Gallery> galleryOptional = galleryRepository.findById(galleryId);
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        boolean result = false;
        if (galleryOptional.isPresent()) {
            Gallery gallery = galleryOptional.get();

            //갤러리 작성자 아이디가 현재 로그인 한 아이디와 같을 때만 수정 가능
            //영송석으로 save()가 필요 없음
            if (gallery.getMember().getId().equals(member.getId())) {
                LocalDate publicDate = DateUtils.toLocalDate(reqDto.getPublicDate(), DateUtils.FORMAT_DATE_BAR);
                gallery.setTitle(reqDto.getTitle());
                gallery.setCover(reqDto.getCover());
                gallery.setIsbn(reqDto.getIsbn());
                gallery.setPublicDate(publicDate.atStartOfDay());
                gallery.setPeriod(reqDto.getPeriod());
                gallery.setRating(reqDto.getRating());
                gallery.setReviewText(reqDto.getReview());
                gallery.setQuote(reqDto.getQuote());
                gallery.setPages(reqDto.getPages());
                result = true;
            }
        }
        return result;

    }

    /**
     * 갤러리 댓글 등록
     * @param userId
     * @param galleryId
     * @param reqDto
     * @return
     */
    public boolean commentRegister(String userId, Long galleryId, CommentReqDto reqDto) {
        Gallery gallery = galleryRepository.findById(galleryId).orElseThrow();
        return commentService.commentRegister(userId, gallery, reqDto);
    }

    /**
     * 내가 작성한 겔러리 수 구하기
     * @param member
     * @return
     */
    public Long getMyGalleryCount(Member member) {
        return galleryRepository.countByMemberId(member.getId());
    }

    public GalleryListRes myGalleryList(String userId) {
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        GalleryListRes res = new GalleryListRes();
        List<Gallery> galleryList = galleryRepository.findByMemberId(member.getId());
        List<GalleryRes> galleryResList = galleryList.stream().map(gallery -> {
            String regTime = DateUtils.getDateTimeString(gallery.getRegTime());
            return GalleryRes.builder()
                    .bookId(gallery.getId())
                    .title(gallery.getTitle())
                    .cover(gallery.getCover())
                    .writer(member.getName())
                    .regTime(regTime)
                    .build();
        }).collect(Collectors.toList());
        res.setBooks(galleryResList);
        return res;
    }
}
