package com.s3_practice.s3_practice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final AmazonS3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    public String upload(MultipartFile image) throws IOException {

        // MultipartFile로부터 원본 파일 이름 추출 -> S3에 저장할 파일 이름 생성
        String originalFileName = image.getOriginalFilename();
        String fileName = changeFileName(originalFileName);

        // S3에 저장할 파일의 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드
        // 저장할 버킷 이름, 파일 이름, 이미지 스트림, 메타 데이터
        s3Client.putObject(bucket, fileName, image.getInputStream(), metadata);

        // 저장된 파일의 접근 url을 받아옴
        return s3Client.getUrl(bucket, fileName).toString();
    }

    // 업로드할 파일 이름 규격화(원본 파일 이름_업로드 시간)
    private String changeFileName(String originalFileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return originalFileName + "_" + LocalDateTime.now().format(formatter);
    }
}