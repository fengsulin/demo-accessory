package com.example.accessory.controller;

import com.example.accessory.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    //使用默认路径
    @RequestMapping("/upload")
    public String upload( MultipartFile file) throws Exception {
        if (file == null) throw new Exception("文件不能为空");
        fileUploadService.upload(file, null);

        return "success";
    }

    //自定义路径
    @RequestMapping("/upload/template")
    public String uploadPlace(MultipartFile file) throws Exception {
        fileUploadService.upload(file, "H:\\upload");

        return null;
    }

    //下载
    @GetMapping("/download/file")
    public String downloadFile(HttpServletResponse response) throws IOException {
        fileUploadService.download(response, "上传模板");

        return null;
    }
}
