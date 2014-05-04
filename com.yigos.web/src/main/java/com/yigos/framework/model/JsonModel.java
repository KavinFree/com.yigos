package com.yigos.framework.model;

public class JsonModel {
    
    /** 返回消息的狀態 */
	private boolean success;

    /** 返回消息的數據 */
	private Object root;

    /** 返回消息的條數 */
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public JsonModel() {
	    super();
	}

	public JsonModel(boolean success) {
		this.success = success;
	}

	public JsonModel(boolean success, Object root) {
		this.success = success;
		this.root = root;
	}

	public JsonModel(boolean success, Object root, int count) {
		this.success = success;
		this.root = root;
		this.count = count;
	}
}
