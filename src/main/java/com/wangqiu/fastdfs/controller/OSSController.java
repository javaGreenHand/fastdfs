package com.wangqiu.fastdfs.controller;

import com.google.api.client.util.IOUtils;
import io.minio.MinioClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @Description 文件上传下载控制器
 * @Author qiu.wang
 * @Date 2020/6/12 12:38
 * @Version 1.0
 **/
@RestController("oss")
public class OSSController {

    private static String url = "http://192.168.235.128:9000";
    private static String accessKey = "admin";
    private static String secretKey = "admin123456";

    @PostMapping("ossUpload")
    public String ossUpload(@RequestParam("fileName") MultipartFile file) {
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            InputStream is = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            minioClient.putObject("test", fileName, is, contentType);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @GetMapping("ossDownload")
    public String ossDownload(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            InputStream fileInputStream = minioClient.getObject("test", fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
            return "下载完成";
        } catch (Exception e) {
            e.printStackTrace();
            return "下载失败";
        }
    }

    @GetMapping("ossUrl")
    public String getUrl() {
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            String url = minioClient.presignedGetObject("file", "test.jpg");
            return url;
        } catch (Exception e) {
            return "获取失败";
        }
    }
}