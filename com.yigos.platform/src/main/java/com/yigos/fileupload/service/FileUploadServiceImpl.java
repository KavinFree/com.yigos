package com.yigos.fileupload.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.yigos.fileupload.util.FilesUtils;
import com.yigos.fileupload.view.FileUploadView;
import com.yigos.framework.common.util.datetime.DateTimeUtil;
import com.yigos.framework.exception.BizException;
import com.yigos.framework.exception.msg.ExceptionConstants;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public FileUploadView upLoad(MultipartFile fileToUpload,HttpServletRequest request,
        HttpServletResponse response, String uploadPath, String root)
        throws Exception {
    	if (fileToUpload.isEmpty()) {	// 上傳文件為空
            throw new BizException(
                "文件不能為空!",
                ExceptionConstants.ERROR_CODE_095,
                ExceptionConstants.BIZ_ERROR,
                new Object[]{"文件不能為空"});
        }
        long fileSize = fileToUpload.getSize();
        if(fileSize>5242880){	//上傳大小不超過5M
            throw new BizException(
                "文件太大!不能超過5M!",
                ExceptionConstants.ERROR_CODE_095,
                ExceptionConstants.BIZ_ERROR,
                new Object[]{"文件太大!不能超過5M!"});
        }
        //獲取文件名稱
    	 String originalFilename = fileToUpload.getOriginalFilename();
         String type = FilesUtils.getFileExtensionWithDot(originalFilename);
         
         // 當前日期
         String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.yyyyMMdd);
         
         // 更改文件名稱
         String fileName = UUID.randomUUID().toString().replace("-", "") + type;
         StringBuffer pathStr = new StringBuffer("");
         pathStr.append(root).append("/").append(uploadPath);
         File path = new File(pathStr.toString());
         if (!path.exists()) {
             path.mkdirs();
         }
         StringBuffer pathStr1 = new StringBuffer("");
         pathStr1.append(path.getAbsolutePath()).append("/").append(currentDate);
         path = new File(pathStr1.toString());
         if (!path.exists()) {
             path.mkdirs();
         }
         StringBuffer pathStr2 = new StringBuffer("");
         pathStr2.append(path).append("/").append(fileName);
         
         
    	File dest = new File(pathStr2.toString());
        fileToUpload.transferTo(dest); //上傳文件
        
    	
        StringBuilder filePath = new StringBuilder()
            .append(uploadPath).append("/")
            .append(currentDate).append("/")
            .append(fileName);
        return new FileUploadView(true, filePath.toString());
    }
    
    
	public void imgPath(HttpServletRequest request, HttpServletResponse response, String path)
			 {
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			FileInputStream fips = null;
			try {
				File file = new File(path);
				fips = new FileInputStream(file);
				byte[] btImg = readStream(fips);
				outputStream.write(btImg);
				outputStream.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fips!=null){
						fips.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(outputStream!=null){
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 读取管道中的流数据
     * @param inStream inStream流数据
     * @return byte[] 
     * @throws Exception
     * @author kavin
     * @since 2014年4月23日
     */
	private byte[] readStream(InputStream inStream) {
		ByteArrayOutputStream bops = null;
		int data = -1;
		try {
			bops = new ByteArrayOutputStream();
			while ((data = inStream.read()) != -1) {
				bops.write(data);
			}
			return bops.toByteArray();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if(inStream!=null){
					inStream.close();
				}
				if(bops!=null){
					bops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
