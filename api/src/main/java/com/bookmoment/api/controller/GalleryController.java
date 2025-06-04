package com.bookmoment.api.controller;

import com.bookmoment.api.dto.req.GalleryReqDto;
import com.bookmoment.api.dto.res.*;
import com.bookmoment.api.repository.GalleryRepository;
import com.bookmoment.api.service.GalleryService;

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
        String keyword = (dto.getKeyword().isEmpty() ? null : dto.getKeyword());
        int currentPage = dto.getCurrentPage();
        int pageSize = dto.getPageSize();
//      3.서비스 호출
        GalleryListRes res = galleryService.findBySearchGallery(keyword, id);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }


    @RequestMapping(value = "/detail/{gNo}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse<GalleryRes>> getDetail(
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

        return null;
    }

    @GetMapping("")
    public GetLikeRes getMethodName(@RequestBody @Valid GetLikeReqDto dto) {

        return this.galleryService.getLikeCount(dto);
    }
    
}
