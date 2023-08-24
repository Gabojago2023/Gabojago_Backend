package com.gabojago.trip.image.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gabojago.trip.image.exception.FileUploadFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileManageUtil {

    private final AmazonS3Client amazonS3Client;
    //@Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3로 파일 업로드
     */
    public String uploadFile(String fileType, MultipartFile multipartFile)
            throws FileUploadFailException {

        String uploadFilePath = fileType + "/" + getFolderName();

        log.debug("uploadFilePath :" + uploadFilePath);

        String originalFileName = multipartFile.getOriginalFilename();

        String uploadFileName = getUuidFileName(originalFileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();

        log.debug("objectMetadata = " + objectMetadata);

        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        // 파일 저장 경로
        String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

        try (InputStream inputStream = multipartFile.getInputStream()) {

            // TODO : 외부에 공개하는 파일인 경우 Public Read 권한을 추가, ACL 확인
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Filed upload failed", e);
            throw new FileUploadFailException("Filed upload failed");
        }

        return keyName;
    }

    /**
     * S3에 업로드된 파일 삭제
     */
    // uploadFileName은 uuidFileName 이다.
    public String deleteFile(String keyName) { // ex) 구분/년/월/일/파일.확장자

        String result = "success";

        try {

            // S3 버킷에 해당 파일 경로에 파일이 있는지 확인
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucketName, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        return result;
    }

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

    /**
     * 년/월/일 폴더명 반환
     */
    public String getFolderName() {
        // SimpleDateFormat을 사용하면 날짜를 원하는 형식으로 얻어 올 수 있다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        System.out.println("sdf = " + sdf);
        Date date = new Date();
        System.out.println("date = " + date);
        String str = sdf.format(date);
        System.out.println("str = " + str);
        return str.replace("-", "/");
    }
}
