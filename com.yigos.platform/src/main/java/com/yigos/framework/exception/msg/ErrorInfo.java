package com.yigos.framework.exception.msg;

public class ErrorInfo {

	/** 訊息方塊標題 */
	private String msgTitle = null;

	/** 是否提示 */
	private boolean bAlert = false;

	/** 彈出提示資訊 */
	private String alertInfo = null;

	/** 頁面顯示資訊 */
	private String promptInfo = null;

	public String getAlertInfo() {
		return alertInfo;
	}

	public void setAlertInfo(String alertInfo) {
		this.alertInfo = alertInfo;
	}

	public String getPromptInfo() {
		return promptInfo;
	}

	public void setPromptInfo(String promptInfo) {
		this.promptInfo = promptInfo;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public void setbAlert(boolean bAlert) {
		this.bAlert = bAlert;
	}

	public boolean isbAlert() {
		return bAlert;
	}

}