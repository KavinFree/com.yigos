package com.yigos.fileupload.view;

/**
 * 文件上傳視圖
 */
public class FileUploadView {
    private boolean success;
    private Object root;
    private String filepath;

    public FileUploadView(boolean success, String filepath) {
        this.success = success;
        this.filepath = filepath;
    }
    public FileUploadView(boolean success, Object root, String filepath) {
        this.success = success;
        this.root = root;
        this.filepath = filepath;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
