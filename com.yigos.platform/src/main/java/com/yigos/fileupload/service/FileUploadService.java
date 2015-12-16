package com.yigos.fileupload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.yigos.fileupload.view.FileUploadView;

public interface FileUploadService {
    
    FileUploadView upLoad(MultipartFile fileToUpload,HttpServletRequest request,
        HttpServletResponse response, String uploadPath, String root)
        throws Exception;
    
    public void imgPath(HttpServletRequest request, HttpServletResponse response, String path)
			throws Exception;
}
