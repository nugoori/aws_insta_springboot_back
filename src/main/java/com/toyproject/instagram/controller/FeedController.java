package com.toyproject.instagram.controller;

import com.toyproject.instagram.dto.UploadFeedReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class FeedController {
    @PostMapping("/feed") // @requestBody = 무조건 JSON 데이터!!
    public ResponseEntity<?> uploadFeed(UploadFeedReqDto uploadFeedReqDto) {
        System.out.println(uploadFeedReqDto);
        uploadFeedReqDto.getFiles().forEach(file -> {
            System.out.println(file.getOriginalFilename());
        });
        return ResponseEntity.ok().body(null);
    }
}
