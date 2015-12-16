package com.yigos.pdf.test;

import java.io.File;
import java.io.FileOutputStream;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfTem {
	public static void main(String[] args){
		PdfReader reader = null;
		PdfStamper stamper = null;
		try {
			reader = new PdfReader("E:/IPS-37.pdf");
			stamper = new PdfStamper(reader, new FileOutputStream(new File(
					"E:/IPS-37-1.pdf")));
			AcroFields form = stamper.getAcroFields();
			form.setField("aaaaa", "2014");
			/*
			form.setField("year[2]", "09"); 
			form.setField("year[6]","4"); 
			form.setField("year[7]", "4");
			form.setField("year[8]", "7"); 
			form.setField("year[9]", "d"); 
			form.setField("sx", "第一學年");
			form.setField("student_name[2]", "001");
			form.setField("student_name[3]", "菜農子弟學校");
			*/
			stamper.setFormFlattening(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stamper.close();
				reader.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
}
