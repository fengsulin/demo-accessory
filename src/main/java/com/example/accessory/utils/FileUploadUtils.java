package com.example.accessory.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUploadUtils {
    private final static int DEFAULT_FILE_SIZE = 50 * 1024 * 1024;
    private final static int FILE_NAME_MAX = 100;

    public static String getDefaultLocation() {
        return DEFAULT_LOCATION;
    }

    public static void setDefaultLocation(String defaultLocation) {
        DEFAULT_LOCATION = defaultLocation;
    }

    private static String DEFAULT_LOCATION = "D:\\accessory\\file";

    public static final String upload(MultipartFile file){
        try {
            return upload(FileUploadUtils.DEFAULT_LOCATION,file,MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public static final String upload(String baseDir,MultipartFile file){
        try {
            return upload(baseDir,file,MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String upload(String baseDir,MultipartFile file,String[] allowedExtension) throws Exception {
        //合法性校验
        assertFileAllowed(file,allowedExtension);
        //处理文件名
        String fileName = encodingFileName(file);
        File desc = getAbsoluteFile(baseDir,fileName);
        file.transferTo(desc);
        return desc.getAbsolutePath();
    }

    private static File getAbsoluteFile(String baseDir, String fileName) throws IOException {
        File desc = new File(baseDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) desc.getParentFile().mkdirs();
        if (!desc.exists()) desc.createNewFile();
        return desc;
    }

    /**
     * 文件名称处理
     * @param file 文件
     * @return
     */
    private static String encodingFileName(MultipartFile file) {
        String dataPath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
        return dataPath + "-" + UUID.randomUUID().toString() + "." + getExtension(file);
    }

    private static void assertFileAllowed(MultipartFile file, String[] allowedExtension) throws Exception {
        if (file.getOriginalFilename() != null){
            int fileNameLength = file.getOriginalFilename().length();
            if(fileNameLength > FILE_NAME_MAX) throw new Exception("文件名过长");
        }
        long size = file.getSize();
        if (size > DEFAULT_FILE_SIZE) throw new Exception("文件过大");
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension,allowedExtension)) throw new Exception("请上传指定类型的文件！");
    }

    /**
     * 获取文件扩展名
     * @param file
     * @return
     */
    private static String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = null;
        if (fileName == null) return null;
        int index = indexOfExtension(fileName);
        extension = index == -1 ? "" : fileName.substring(index + 1);
        if (extension.isEmpty()) extension = MimeTypeUtils.getExtension(file.getContentType());
        return extension;
    }

    /**
     * 获取符号“.”的位置
     * @param fileName
     * @return
     */
    private static int indexOfExtension(String fileName) {
        if(fileName == null) return -1;
        int extensionPos = fileName.lastIndexOf(".");
        int lastSeparator = indexOfLastSeparator(fileName);
        return lastSeparator > extensionPos ? -1 : extensionPos;
    }

    /**
     * 返回文件分隔符的位置
     * @param fileName
     * @return 末尾路径分隔符（unix/windows）的位置
     */
    private static int indexOfLastSeparator(String fileName) {
        if (fileName == null) return -1;
        int lastUnixPos = fileName.lastIndexOf("/");
        int latWindowsPos = fileName.lastIndexOf("\\");
        return Math.max(latWindowsPos,latWindowsPos);
    }

    /**
     * 文件类型校验
     * @param extension
     * @param allowedExtension
     * @return
     */
    private static boolean isAllowedExtension(String extension,String[] allowedExtension) {
        for (String str : allowedExtension){
            if (str.equalsIgnoreCase(extension)) return true;
        }
        return false;
    }


}
