package com.yigos.fileupload.util;

public final class FilesUtils {
    
    /**
     * 獲取.后缀名
     * @param fileName 文件名稱
     * @return String
     * @author JasonTan
     * @since 2014年3月12日
     */
    public static String getFileExtensionWithDot(String fileName) {
        if (fileName.indexOf('.') != -1) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return fileName;
    }

}