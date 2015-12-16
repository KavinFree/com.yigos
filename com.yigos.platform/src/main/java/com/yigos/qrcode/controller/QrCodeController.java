package com.yigos.qrcode.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.qrcode.util.QrCodeEncoderHandler;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class QrCodeController extends BaseControllerImpl {

	@RequestMapping
	public void getQrCode(HttpServletResponse response) throws Exception {
		QrCodeEncoderHandler qrCode = new QrCodeEncoderHandler();
		File logoImg = null;
		BufferedImage image = qrCode.encode("1234567890", logoImg, 300, 300);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		response.setContentType("image/png");  
        ImageIO.write(image, "png", response.getOutputStream());
	}
}
