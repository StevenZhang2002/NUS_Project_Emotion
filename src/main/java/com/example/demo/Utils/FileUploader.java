package com.example.demo.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.util.UUID;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

public class FileUploader {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        uploadFile();
    }

    private static void uploadFile() throws InvalidKeyException, IOException, NoSuchAlgorithmException {
        try {
            // 配置 Minio 服务连接信息
            String endpoint = "http://122.51.221.6:31090";
            String accessKey = "ZH5vn9AR4ZunA8qxiKBj";
            String secretKey = "72KBoRg6fAfvp6ofYdRn05TjJWHfkugrG1nb9ksR";
            String bucketName = "moodiary";

            // 使用 MinIO 服务的 URL，Access key 和 Secret key 创建一个 MinioClient 对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个存储桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created.");
            }

            // 上传文件路径配置，使用本地图片的绝对路径
            File file = new File("C:\\Users\\22341\\Pictures\\Saved Pictures\\Resident-Evil-4-Remake-1.jpg");
            long size = file.length();
            String fileName = file.getName();
            InputStream is = new FileInputStream(file);

            // 设置上传文件的路径，将文件存储到 "product" 文件夹下，不包含日期
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
            String fileUploadPath = "product/" + uuid + "file01" + fileName.substring(fileName.lastIndexOf("."));

            // 使用 PutObjectArgs 上传文件，新的 API 接口方式
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUploadPath)
                            .stream(is, size, -1) // 设置输入流和大小
                            .contentType("image/png") // 根据你的文件类型设置 contentType
                            .build()
            );

            System.out.println(file.getAbsolutePath() + " is successfully uploaded as 【" + fileUploadPath + "】 to 【" + bucketName + "】bucket.");

            // 生成公开的 URL
            String publicUrl = endpoint + "/" + bucketName + "/" + fileUploadPath;
            System.out.println("Public URL: " + publicUrl);

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
