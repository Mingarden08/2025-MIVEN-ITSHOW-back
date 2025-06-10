package com.bookmoment.api.controller;

import com.bookmoment.api.dto.req.CommentReqDto;
import com.bookmoment.api.dto.req.GalleryRegReqDto;
import com.bookmoment.api.dto.req.GalleryReqDto;
import com.bookmoment.api.dto.req.LikeItReqDto;
import com.bookmoment.api.dto.res.*;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.service.CommentService;
import com.bookmoment.api.service.GalleryService;
import com.bookmoment.api.service.LikeItService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookmoment.api.dto.req.GetLikeReqDto;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@Slf4j
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private LikeItService likeItService;

    @PostMapping("/list")
    public ResponseEntity<DataResponse<GalleryListRes>> getGalleryList(@RequestBody GalleryReqDto dto,
                                                                       @Parameter(hidden = true) Authentication authentication,
                                                                       HttpServletRequest request) {
//      1.로그인 여부 체크
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();
//      2.ReqDto 데이터 값 체크(keyword)
        String keyword = (dto.getKeyword().isEmpty() ? null : dto.getKeyword().trim());
//      3.서비스 호출
        GalleryListRes res = null;
        try {
            res = galleryService.galleryList(id, keyword);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } catch (Exception e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_VALID, res));
        }
    }


    @RequestMapping(value = "/detail/{gNo}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse<GalleryDetailRes>> getDetail(
            @PathVariable @Parameter(description = "갤러리 고유번호", required = true) Long gNo,
            @Parameter(hidden = true) Authentication authentication,
            HttpServletRequest request)
    {
//      1.로그인 여부 체크
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();
        //서비스 호출
        GalleryDetailRes res = null;
        try {
            res = galleryService.galleryDetail(id, gNo);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } catch (NullPointerException e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_FOUND_GALLERY, res));
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<DataResponse<Map<String, Boolean>>> galleryRegister(@Parameter(hidden = true) Authentication authentication,
                                                                              HttpServletRequest request,
                                                                              @RequestBody GalleryRegReqDto reqDto) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        boolean result = false;
        Map<String, Boolean> res = new HashMap<>();
        try {
            result = galleryService.galleryRegister(id, reqDto);
            res.put("success", result);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } catch (Exception e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_FOUND_USER, res));
        }
    }

    @PatchMapping("/update/{gNo}")
    public ResponseEntity<DataResponse<Map<String, Boolean>>> galleryUpdate(@PathVariable @Parameter(description = "갤러리 고유번호", required = true) Long gNo,
                                                                            @Parameter(hidden = true) Authentication authentication,
                                                                            HttpServletRequest request,
                                                                            @RequestBody GalleryRegReqDto reqDto) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        boolean result = galleryService.galleryUpdate(id, gNo, reqDto);
        Map<String, Boolean> res = new HashMap<>();
        res.put("success", result);
        if (result) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } else {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.FORBIDDEN, res));
        }
    }


    @PostMapping("/{gNo}/comment")
    public ResponseEntity<DataResponse<Map<String, Boolean>>> commentRegister(@PathVariable @Parameter(description = "갤러리 고유번호", required = true) Long gNo,
                                                                              @Parameter(hidden = true) Authentication authentication,
                                                                              HttpServletRequest request,
                                                                              @RequestBody CommentReqDto reqDto) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        boolean result = false;
        Map<String, Boolean> res = new HashMap<>();
        try {
            result = galleryService.commentRegister(id, gNo, reqDto);
            res.put("success", result);
            if (result) {
                return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
            } else {
                return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_VALID, res));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.FORBIDDEN, res));
        }

    }


    @PostMapping("/like")
    public ResponseEntity<DataResponse<Map<String, Integer>>> LikeList(@Parameter(hidden = true) Authentication authentication,
                                                                       HttpServletRequest request,
                                                                       @RequestBody LikeItReqDto reqDto) {

        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();
        Map<String, Integer> res = new HashMap<>();
        int likeCount = 0;
        try {
            likeCount = likeItService.getLikeCount(id, reqDto);
            if (likeCount < -1) {
                return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_VALID, res));
            } else {
                res.put("likeCount", likeCount);
                return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.NOT_FOUND_GALLERY, res));
        }
    }

    @PostMapping("/mylist")
    public ResponseEntity<DataResponse<GalleryListRes>> getMyGalleryList(@Parameter(hidden = true) Authentication authentication,
                                                                         HttpServletRequest request) {

        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();
        GalleryListRes res = null;
        try {
            res = galleryService.myGalleryList(id);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } catch (Exception e) {
            return ResponseEntity.ok(DataResponse.of(ResponseCode.FORBIDDEN, res));
        }
    }

}
