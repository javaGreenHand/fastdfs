package com.wangqiu.fastdfs.controller;

import com.wangqiu.fastdfs.utils.FastDFSClient;
import com.wangqiu.fastdfs.vo.FastDFSFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 文件上传下载控制器
 * @Author qiu.wang
 * @Date 2020/6/12 12:38
 * @Version 1.0
 **/
@RestController
public class FastDFSController {

    private static Logger logger = LoggerFactory.getLogger(FastDFSController.class);

    @PostMapping("/dfsUpload")
    public String dfsUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
           return "文件不能为空！";
        }
        String path = "";
        try {
            path = saveFile(file);
        } catch (Exception e) {
            logger.error("upload file failed", e);
        }
        return path;
    }

    @GetMapping("/dfsDownload")
    public void dfsDownload(@RequestParam("filePath") String filePath, HttpServletResponse response) {
        try {
            byte[] file = FastDFSClient.downFile("group1", filePath);
            response.getOutputStream().write(file);
        } catch (Exception e) {
            logger.error("download file failed", e);
        }
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }
}
