package com.yigos.framework.exception;

/**
 * 返回給客戶看的異常
 * @since 2013年5月6日 
 */
public class BizException extends CustomException {

	private static final long serialVersionUID = 4734196001788893113L;

	/**錯誤碼，需統一編寫 **/
	private String errorCodeString = "";
	/**錯誤類型，程式員判斷出可能是什麼錯誤類型的時候提供*/
	private String errorType = null;
	/**需要傳遞的異常 **/
	private Exception exception = null;
	/**需要動態傳遞的參數，這個不用國際化，由程序動態生成*/
	Object[] arg = null;
	
	
	 
	 /**
	 * 構造器
	 */
	public BizException() {
		super();
	}
	 
	 /**
	 * 必須調用這個構造方法來構造帶錯誤碼的exception，用於自己構建的異常
	 * @param msg
	 * @param errorCode
	 * @param errorType
	 * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.SYS_ERROR
	 * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.DB_ERROR
	 * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.UNKNOWN_ERROR
	 */
	public BizException(String msg, String errorCode, String errorType) {
		super(msg);
		setErrorCodeString(errorCode);
		setErrorType(errorType);
	}
     
     /**
      * 必須調用這個構造方法來構造帶錯誤碼的exception，用於自己構建的異常且帶上參數信息
     * @param msg
     * @param errorCode
     * @param errorType
     * @param arg
     * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.SYS_ERROR
     * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.DB_ERROR
     * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.UNKNOWN_ERROR
     */
    public BizException(String msg, String errorCode, String errorType,
        Object[] arg) {
        super(msg);
        setErrorCodeString(errorCode);
        setErrorType(errorType);
        setArg(arg);
    }
     
     /**
      * 必須調用這個構造方法來構造帶錯誤碼的exception，用於包裝的異常
      * @param msg
      * @param errorCode
      * @param errorType
      * @param e
      * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.SYS_ERROR
	  * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.DB_ERROR
	  * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.UNKNOWN_ERROR
      */
    public BizException(String msg, String errorCode, String errorType,
        Exception e) {
		super(msg);
		setErrorCodeString(errorCode);
		setErrorType(errorType);
		setException(e);
	}
     /**
      * 必須調用這個構造方法來構造帶錯誤碼的exception，用於包裝的異常
      * @param msg
      * @param errorCode
      * @param errorType
      * @param e
      * @param arg
      * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.SYS_ERROR
      * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.DB_ERROR
      * @see com.yigos.framework.exception.msg.biz.sky.b2c.framework.exception.ExceptionConstants.UNKNOWN_ERROR
      */
    public BizException(String msg, String errorCode, String errorType,
        Exception e, Object[] arg) {
        super(msg);
        setErrorCodeString(errorCode);
        setErrorType(errorType);
        setException(e);
        setArg(arg);
    }
	public String getErrorCodeString() {
		return errorCodeString;
	}

	public void setErrorCodeString(String errorCode) {
		this.errorCodeString = errorCode;
	}

    
    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    public Object[] getArg() {
        return arg;
    }
    public void setArg(Object[] arg) {
        this.arg = arg;
    }

}

