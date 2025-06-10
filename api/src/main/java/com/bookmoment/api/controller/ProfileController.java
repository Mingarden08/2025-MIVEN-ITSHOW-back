package com.bookmoment.api.controller;


import com.bookmoment.api.dto.req.CommentReqDto;
import com.bookmoment.api.dto.req.PatchProfileReqDto;
import com.bookmoment.api.dto.res.DataResponse;
import com.bookmoment.api.dto.res.ProfileRes;
import com.bookmoment.api.dto.res.ResponseCode;
import com.bookmoment.api.service.FileService;
import com.bookmoment.api.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final FileService fileService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {"multipart/form-data"})
    public ResponseEntity<DataResponse<?>> patchProfile(@Parameter(hidden = true) Authentication authentication,
                                                        HttpServletRequest request,
                                                        @Parameter(description = "file") @RequestParam("file") MultipartFile file) throws MalformedURLException, BadRequestException {
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }

        String id = authentication.getName();

        try {
           String url =  fileService.uploadFile(file);
           if (url == null || url.isEmpty() || !url.contains("http")) {
               return null;
           }

            boolean result = profileService.updateImage(id, url); //프로필 이미지 저장
            Map<String, Boolean> res = new HashMap<>();
            res.put("success", result);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));

        } catch (BadRequestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PatchMapping("")
    public ResponseEntity<DataResponse<?>> patchProfile(@Parameter(hidden = true) Authentication authentication,
                                                        HttpServletRequest request,
                                                        @RequestBody PatchProfileReqDto reqDto){
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }

        String id = authentication.getName();

        boolean result = profileService.patchProfile(id, reqDto);
        
        Map<String, Boolean> res = new HashMap<>();
        if (result) { // 프로필 수정 성공   
            res.put("success", result);
            return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
        } else { // 프로필 수정 실패
            res.put("success", false);
            return ResponseEntity.badRequest().body(DataResponse.of(ResponseCode.NOT_ENTERED_REQUEST_BODY_FIELD, "Failed to update data"));
        }

    }

    @GetMapping("")
    public ResponseEntity<DataResponse<ProfileRes>> getProfile(@Parameter(hidden = true) Authentication authentication,
                                                      HttpServletRequest request) {
        // 회원체크
        if (authentication == null) {
            throw new IllegalStateException("Authentication object is null");
        }
        String id = authentication.getName();

        // 서비스 호출
        ProfileRes res = profileService.getProfile(id);
        return ResponseEntity.ok(DataResponse.of(ResponseCode.SUCCESS, res));
    }
}
