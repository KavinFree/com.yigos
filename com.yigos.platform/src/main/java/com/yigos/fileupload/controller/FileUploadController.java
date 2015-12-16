package com.yigos.fileupload.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.yigos.fileupload.service.FileUploadService;
import com.yigos.fileupload.view.FileItemsView;
import com.yigos.fileupload.view.FileUploadView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;
import com.yigos.framework.common.util.JsonUtils;

/**
 * 文件上傳 控制層
 */
@Controller
public class FileUploadController extends BaseControllerImpl {

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping
	public void upload(MultipartFile fileToUpload, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		FileUploadView fileUploadView = null;
		response.setContentType("text/html;charset=utf-8");
		fileUploadView = this.fileUploadService.upLoad(fileToUpload, request,
				response, "uploads", DataDict.UPLOAD_PATH);
		fileUploadView.setRoot("上传文件成功");
		response.getWriter().write(JsonUtils.toJsonString(fileUploadView));
	}

	@RequestMapping
	public void imgPath(HttpServletRequest request,
			HttpServletResponse response, String path) throws Exception {
		this.fileUploadService.imgPath(request, response, DataDict.UPLOAD_PATH + "/"
				+ path);
	}
	
	@RequestMapping
	public void uploadFileItems(Model model, FileItemsView view){
		System.err.println("Test upload: " + view.getFiles()[0].getOriginalFilename()); 
	}
	
}
