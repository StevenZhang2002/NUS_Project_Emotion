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
import io.minio.errors.*;

public class FileUploader {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        String directoryPath = "D:\\Picture"; // 指定要上传的文件夹路径
        uploadFilesFromDirectory(directoryPath);
    }

    private static void uploadFilesFromDirectory(String directoryPath) throws InvalidKeyException, IOException, NoSuchAlgorithmException {
        try {
            // 配置 Minio 服务连接信息
            String endpoint = "http://122.51.221.6:31090";
            String accessKey = "xhkxRJ36nIudfXnpkF1M";
            String secretKey = "0sPtBWMCXh1mX8x46ByxR4wKInKEIglPgeNZCo3Q";
            String bucketName = "moodiary";

            // 创建 MinioClient 对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查存储桶是否存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created.");
            }

            // 获取文件夹下的所有文件
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    // 仅处理图片文件（可根据文件扩展名过滤，例如 jpg, png）
                    if (file.isFile() && isImageFile(file)) {
                        uploadSingleFile(minioClient, bucketName, file);
                    }
                }
            } else {
                System.out.println("The directory is empty or not accessible.");
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

    private static void uploadSingleFile(MinioClient minioClient, String bucketName, File file) throws IOException {
        long size = file.length();
        String fileName = file.getName();
        InputStream is = new FileInputStream(file);

        // 设置上传文件的路径
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        String fileUploadPath = fileName;

        // 上传文件
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileUploadPath)
                            .stream(is, size, -1) // 设置输入流和大小
                            .contentType("image/png") // 根据需要调整文件类型
                            .build()
            );

            System.out.println(file.getAbsolutePath() + " is successfully uploaded as 【" + fileUploadPath + "】 to 【" + bucketName + "】bucket.");
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } finally {
            is.close();
        }
    }

    // 判断文件是否为图片格式
    private static boolean isImageFile(File file) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
