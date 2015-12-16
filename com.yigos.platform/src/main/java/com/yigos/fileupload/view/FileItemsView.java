package com.yigos.fileupload.view;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileItemsView {
	private CommonsMultipartFile[] files;

	public CommonsMultipartFile[] getFiles() {
		return files;
	}

	public void setFile(CommonsMultipartFile[] files) {
		this.files = files;
	}
}
