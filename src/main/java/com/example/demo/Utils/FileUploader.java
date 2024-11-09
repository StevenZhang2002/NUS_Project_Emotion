package com.example.demo.Utils;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;

import java.io.InputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUploader {

    // MinIO 配置信息，写死在类中
    private static final String ENDPOINT = "http://122.51.221.6:31090";
    private static final String ACCESS_KEY = "xhkxRJ36nIudfXnpkF1M";
    private static final String SECRET_KEY = "0sPtBWMCXh1mX8x46ByxR4wKInKEIglPgeNZCo3Q";
    private static final String BUCKET_NAME = "moodiary";

    private MinioClient minioClient;

    // 无参构造方法，使用类内的配置信息
    public FileUploader() {
        this.minioClient = MinioClient.builder()
                .endpoint(ENDPOINT)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
        initializeBucket();
    }

    // 初始化 Bucket，如果不存在则创建
    private void initializeBucket() {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                System.out.println("Bucket created: " + BUCKET_NAME);
            } else {
                System.out.println("Bucket already exists: " + BUCKET_NAME);
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
                            .bucket(BUCKET_NAME)
                            .object(fileUploadPath)
                            .stream(inputStream, size, -1)
                            .contentType("image/png") // 根据需要调整内容类型
                            .build()
            );

            // 生成预签名的 URL
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)  // 使用 MinIO 的 Method 枚举
                            .bucket(BUCKET_NAME)
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
