package com.example.accessory.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileUploadService {
    void upload(MultipartFile file, String baseDir) throws Exception;

    void download(HttpServletResponse response , String newName) throws IOException;
}