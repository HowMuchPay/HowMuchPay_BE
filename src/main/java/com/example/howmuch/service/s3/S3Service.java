package com.example.howmuch.service.s3;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.howmuch.domain.entity.User;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public User saveFile(MultipartFile multipartFile) throws RuntimeException {
        String translatedFileName = uploadFileToS3(multipartFile);
        return User.builder().profileImage(translatedFileName).build();
    }

    private String uploadFileToS3(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String translatedFileName = translateFileName(originalFilename);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, translatedFileName, multipartFile.getInputStream(), new ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3Client.putObject(putObjectRequest);

            return translatedFileName;
        } catch (IOException e) {
            throw new RuntimeException("File translation failed", e);
        }
    }

    private String translateFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + FILE_EXTENSION_SEPARATOR + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return originalFilename.substring(pos + 1);
    }
}