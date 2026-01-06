package com.swapandgo.sag.service.S3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private static final String ITEM_IMAGE_FOLDER = "items";
    private final S3Client s3Client;

    @Value("{cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    //상픔 이미지 업로드
    public String uploadItemImage(MultipartFile file){
        validateImageFile(file);

        String fileName = generateItemImageKey(file);

        try{
            //AWS S3 파일을 업로드 요청 객체
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(
                    putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
            String imageUrl = generateImageUrl(fileName);
            log.info("상품 이미지 업로드 성공: {}", imageUrl);
            return imageUrl;
        }catch (IOException e){
            log.error("상품 이미지 업로드 실패", e);
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }
    }




    //파일 검증
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()){
            throw new IllegalArgumentException("파일이 비었습니다.");
        }

        long maxSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }


    }

    //S3 object key 생성 - items/uuid.jpg
    private String generateItemImageKey(MultipartFile file) {
        String extension = extractExtension(file.getOriginalFilename());
        return ITEM_IMAGE_FOLDER + "/" + UUID.randomUUID() + extension;
    }

    private String extractExtension(String filename) {
        if(filename == null || !filename.contains(".")){
            throw new IllegalArgumentException("파일 확장자가 존재하지 않습니다.");
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private String generateImageUrl(String fileName) {
        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucket, region, fileName
        );
    }
}
