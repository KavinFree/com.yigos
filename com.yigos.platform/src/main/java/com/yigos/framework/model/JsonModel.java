package com.yigos.framework.model;

public class JsonModel {
    
    /** 返回消息的狀態 */
	private boolean success;
	
	/** 返回消息的狀態码 */
	private int code;

	/** 返回消息的數據 */
	private Object result;
	
	private long totalCount;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public JsonModel() {
	    super();
	}

	public JsonModel(boolean success, int code, Object result, long totalCount) {
		super();
		this.success = success;
		this.code = code;
		this.result = result;
		this.totalCount = totalCount;
	}
}
