package com.bookmoment.api.controller;

import com.bookmoment.api.dto.req.CommentReqDto;
import com.bookmoment.api.dto.req.GalleryRegReqDto;
import com.bookmoment.api.dto.req.GalleryReqDto;
import com.bookmoment.api.dto.res.*;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.service.CommentService;
import com.bookmoment.api.service.GalleryService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@Slf4j
public class GalleryController {
    //    private final GalleryRepository galleryRepository;
//
//    public GalleryController(GalleryRepository galleryRepository) {
//        this.galleryRepository = galleryRepository;
//    }
    @Autowired
    private GalleryService galleryService;

    @Autowired
    private CommentService commentService;

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
        GalleryListRes res = galleryService.galleryList(id, keyword);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
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
        GalleryDetailRes res = galleryService.galleryDetail(id, gNo);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }

    @PostMapping("/regist")
    public ResponseEntity<DataResponse<?>> galleryRegister(@Parameter(hidden = true) Authentication authentication,
                                                           HttpServletRequest request,
                                                           @RequestBody GalleryRegReqDto reqDto) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        boolean result = galleryService.galleryRegister(id, reqDto);
        Map<String, Boolean> res = new HashMap<>();
        res.put("success", result);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }

    @PatchMapping("/update/{gNo}")
    public ResponseEntity<DataResponse<?>> galleryUpdate(@PathVariable @Parameter(description = "갤러리 고유번호", required = true) Long gNo,
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
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }


    @PostMapping("/{gNo}/comment")
    public ResponseEntity<DataResponse<?>> commentRegister(@PathVariable @Parameter(description = "갤러리 고유번호", required = true) Long gNo,
                                                           @Parameter(hidden = true) Authentication authentication,
                                                           HttpServletRequest request,
                                                           @RequestBody CommentReqDto reqDto) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        boolean result = galleryService.commentRegister(id, gNo, reqDto);
        Map<String, Boolean> res = new HashMap<>();
        res.put("success", result);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }
}
