package com.bookmoment.api.service;

import com.bookmoment.api.dto.res.GalleryListRes;
import com.bookmoment.api.dto.res.GalleryRes;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.repository.MemberRepository;
import com.bookmoment.api.util.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;

    private final MemberRepository memberRepository;

    /**
     * 내가 작성한 갤러리 목록 조회
     * @return
     */
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

    public GalleryListRes findBySearchGallery(String keyword, String userId) {
        GalleryListRes galleryListRes = new GalleryListRes();
        // 회원정보 조회
        Optional<Member> memberOptional = memberRepository.findByEmail(userId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Long id = member.getId(); // member PK
            List<Gallery> galleryList = (keyword == null) ? galleryRepository.findByMemberInfo_id(id) : galleryRepository.findByMemberInfo_idAndTitleContaining(id, keyword);
            List<GalleryRes> galleryResList = galleryList.stream().map(gallery -> {
                return GalleryRes.builder()
                        .pages(gallery.getPages())
                        .like(gallery.getLikeList().stream().count())
                        .quote(gallery.getQuote())
                        .isbn(gallery.getIsbn())
                        .title(gallery.getTitle())
                        .date(DateUtils.getLocalDateTimeString(gallery.getDate(), DateUtils.FORMAT_DATE_UNIT_BAR))
                        .cover(gallery.getCover())
                        .reviewText(gallery.getReviewText())
                        .rating(gallery.getRating())
                        .bookId(gallery.getBookId())
                        .period(gallery.getPeriod())
                        .writer(member.getName())
                        .quoteDate(DateUtils.getLocalDateTimeString(gallery.getDate(), DateUtils.FORMAT_DATE_UNIT_BAR))
                        .build();
            }).collect(Collectors.toList());
            galleryListRes.setBooks(galleryResList);
        }
        return galleryListRes;
    }

    /**
     * 선택한 갤러리 상세 조회
     * @param id
     * @return
     */
    public GalleryRes getGalleryDetail(Long galleryId, String userId) {
        Optional<Member> memberOptional = memberRepository.findByEmail(userId);
        Member member = memberOptional.get();
        Long id = member.getId(); // member PK

        //레파지토리를 검색
        Optional<Gallery> galleryOptional = galleryRepository.findByIdAndMemberInfo_id(galleryId, id);

        //없으면 없는 처리

        //있으면 dto변환 처리
        return null;
    }


}
