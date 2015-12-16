package com.yigos.qrcode.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeEncoderHandler {

	/**
	 * 编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public BufferedImage encode(String contents, File logoImg, int width, int height) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 二维码边界空白大小 ，如：1、2、3、4 默认好像是4
		hints.put(EncodeHintType.MARGIN, 1);
		// start guard left bars middle guard right bars end guard
		
		BufferedImage image = null;
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);
			image = toBufferedImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Graphics2D g = image.createGraphics();
		logoImg(logoImg, g, width, height);
		return image;
	}

	private static final int BLACK = -16777216;
	private static final int WHITE = -1;

	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	public static void logoImg(File logoImg, Graphics2D g, int width, int height){
		//读取logo图片
		BufferedImage logoBuf = null;
		try {
			logoBuf = ImageIO.read(logoImg);
			//设置二维码大小，太大，会覆盖二维码，此处20%
			int logoWidth = logoBuf.getWidth(null) > width*2 /10 ? (width*2 /10) : logoBuf.getWidth(null);
			int logoHeight = logoBuf.getHeight(null) > height*2 /10 ? (height*2 /10) : logoBuf.getHeight(null);
			//设置logo图片放置位置
			//中心
			int x = (width - logoWidth) / 2;
			int y = (height - logoHeight) / 2;
			
			//开始合并绘制图片
			g.drawImage(logoBuf, x, y, logoWidth, logoHeight, null);
			g.drawRoundRect(x, y, logoWidth, logoHeight, 15 ,15);
			//logo边框大小
			g.setStroke(new BasicStroke(2));
			//logo边框颜色
			g.setColor(Color.WHITE);
			g.drawRect(x, y, logoWidth, logoHeight);
			g.dispose();
			logoBuf.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String contents = "http://weixin.91just.cn/weixin/topic.html?id=ff808081506ec52101506f69f1f700d7";
		int width = 300, height = 300;
		QrCodeEncoderHandler handler = new QrCodeEncoderHandler();
		File logoImg = new File("./qrcode/logo.png");
		BufferedImage image = handler.encode(contents, logoImg, width, height);
		
		File file = new File("./qrcode/test.png");
		ImageIO.write(image, "png", file);
	}

}