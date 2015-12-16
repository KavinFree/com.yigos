package com.yigos;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class PostData {
	public static void main(String[] args) throws Exception{
		String responseContent = "";
		FileBody file = new FileBody(new File("E:/webdata/11.spx"));//12.mp3
		MultipartEntity multEntity = new MultipartEntity();
		multEntity.addPart("fileToUpload", file);
		StringBody comment = new StringBody("12.mp3"); 
		multEntity.addPart("comment", comment);
		responseContent = httpPost("http://127.0.0.1:88/yigos/fileupload/file-upload/upload.json", multEntity, responseContent);
		System.out.println(responseContent);
	}
	
	public static String httpPost(String uri, MultipartEntity multEntity, String responseContent) throws Exception {
		// (1) Post请求
		HttpPost post = new HttpPost(uri);

		post.setEntity(multEntity);
		// (2) 添加请求头信息  
		post.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");  
		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  

		// (3) 发送请求
		HttpClient http = new DefaultHttpClient();
		HttpResponse response = http.execute(post);

		// (4) 读取返回结果
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			responseContent = readResponse(in, responseContent);
		}
		return responseContent;
	}
	
	public static String readResponse(InputStream in, String responseContent) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			responseContent = responseContent + line;
		}
		return responseContent;
	}
}
