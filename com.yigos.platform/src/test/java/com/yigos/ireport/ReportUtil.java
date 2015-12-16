package com.yigos.ireport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class ReportUtil {

	public static String jasperFilePath = "E:\\document\\report\\report1.jasper";// 模版文件

	public static String pdfFilePath = "E:\\document\\report\\report1.pdf";// 生成pdf文件

	public static String docxFilePath = "E:\\document\\report\\report1.docx";// 生成wod文件

	public static String xlsxFilePath = "E:\\document\\report\\report1.xlsx";// 生成excel文件

	/**
	 * 生成文件pdf
	 */
	public static void addPdf() {
		try {
			// 第一步：装载jasper文件
			File jasperFileName = new File(jasperFilePath);
			// 第二步：设置参数值
			/* 设置参数 */
			HashMap<String, Object> params = new HashMap<String, Object>();// 建立参数表
			params.put("name", "我们的产品"); // 设置参数值
			// 第三步：利用JasperRunManager生成PDF文件
			JasperRunManager.runReportToPdfFile(jasperFileName.getPath(),
			params, new JREmptyDataSource());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * 生成文件word
	 */

	public static void addDocx() {
		File jasperFileName = new File(jasperFilePath);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "我们的产品");
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
			jasperFileName.getPath(), params, new JREmptyDataSource());
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			JRAbstractExporter exporter = new JRDocxExporter();// 可以替换成不同的文件类型
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.exportReport();
			byte[] bytes = oStream.toByteArray();
			if (bytes != null && bytes.length > 0) {
				OutputStream ouputStream = new FileOutputStream(new File(
				docxFilePath));
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			} else {

			}

		} catch (Exception ex) {

		}

	}

	/**
	 * 
	 * 生成文件excel
	 */

	public static void addXls() {
		File jasperFileName = new File(jasperFilePath);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "我们的产品");
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
			jasperFileName.getPath(), params, new JREmptyDataSource());
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();// 可以替换成不同的文件类型
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.setParameter(
			JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
			Boolean.TRUE); // 删除记录最下面的空行
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			Boolean.FALSE);// 删除多余的ColumnHeader
			exporter.setParameter(
			JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
			Boolean.FALSE);// 显示边框
			exporter.exportReport();
			byte[] bytes = oStream.toByteArray();
			if (bytes != null && bytes.length > 0) {
				OutputStream ouputStream = new FileOutputStream(new File(
				xlsxFilePath));
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			} else {

			}
		} catch (Exception ex) {

		}
	}

	/**
	 * 
	 * 测试
	 */
	public static void main(String[] args) {
		// 生成pdf文件
		//ReportUtil.addPdf();
		// 生成word文件
		//ReportUtil.addDocx();
		// 生成excel文件
		ReportUtil.addXls();
	}

}