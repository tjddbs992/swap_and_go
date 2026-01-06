package com.swapandgo.sag.api.auth.image;

import com.swapandgo.sag.dto.image.MultipleImageUploadResponse;
import com.swapandgo.sag.service.S3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<MultipleImageUploadResponse> uploadImages(
            @RequestParam("images") List<MultipartFile> images
            ){
        if (images.size() > 5){
            throw new IllegalArgumentException("이미지는 최대 5개까지 업로드 가능합니다.");
        }
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : images){
            String imageUrl = s3Service.uploadItemImage(file);
            imageUrls.add(imageUrl);
        }

        return ResponseEntity.ok(new MultipleImageUploadResponse(imageUrls));
    }
}
