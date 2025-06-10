package com.bookmoment.api.service;

import com.bookmoment.api.dto.req.CommentReqDto;
import com.bookmoment.api.dto.res.CommentListRes;
import com.bookmoment.api.dto.res.CommentRes;
import com.bookmoment.api.entity.Comment;
import com.bookmoment.api.entity.Gallery;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.repository.CommentRepository;
import com.bookmoment.api.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    /**
     * 갤러리 댓글 조회
     * @param galleryId
     * @return
     */
    public CommentListRes getComments(Long galleryId) {
        CommentListRes commentList = new CommentListRes();
        List<Comment> comments = commentRepository.findByGalleryIdOrderByIdDesc(galleryId);
        List<CommentRes> res = comments.stream().map(comment -> {
            Member member = memberRepository.getOne(comment.getWriter().getId());
            return CommentRes.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .writer(member.getName())
                    .likeCount(comment.getLikeList().size())
                    .build();
        }).collect(Collectors.toList());
        commentList.setComments(res);
        return commentList;
    }

    /**
     * 갤러리 댓글 등록
     * @param userId
     * @param gallery
     * @param reqDto
     * @return
     */
    public boolean commentRegister(String userId, Gallery gallery, CommentReqDto reqDto) {
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        if (reqDto.getText() == null || reqDto.getText().trim().isEmpty()) {
            return false;
        } else {
            Comment comment = reqDto.toEntity(member, gallery);
            commentRepository.save(comment);
            return true;
        }
    }
}
