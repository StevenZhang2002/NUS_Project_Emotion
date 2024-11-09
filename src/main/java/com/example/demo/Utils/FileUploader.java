package com.example.demo.Utils;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.io.InputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUploader {

    private MinioClient minioClient;
    private String bucketName;

    public FileUploader(String endpoint, String accessKey, String secretKey, String bucketName) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        this.bucketName = bucketName;
        initializeBucket();
    }

    private void initializeBucket() {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created: " + bucketName);
            } else {
                System.out.println("Bucket already exists: " + bucketName);
            }
        } catch (Exception e) {
            System.err.println("Error in bucket initialization: " + e.getMessage());
        }
    }

    // 上传文件并返回预签名 URL
    public String uploadFile(InputStream inputStream, String originalFileName, long size) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        String fileUploadPath = uuid + "_" + originalFileName;

        try {
            // 上传文件到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUploadPath)
                            .stream(inputStream, size, -1)
                            .contentType("image/png") // 根据需要调整内容类型
                            .build()
            );

            // 生成预签名的 URL
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)  // 使用 MinIO 的 Method 枚举
                            .bucket(bucketName)
                            .object(fileUploadPath)
                            .build()
            );

            System.out.println("File successfully uploaded as: " + fileUploadPath);
            return url;  // 返回生成的预签名 URL
        } catch (Exception e) {
            System.err.println("Error occurred while uploading file: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            inputStream.close();
        }
    }
}
