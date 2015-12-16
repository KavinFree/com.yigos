package com.yigos.framework.exception.msg;

import javax.servlet.http.HttpServletRequest;

public interface ErrorMsgSrv {
    /**
     * 按國際化方式獲取錯誤碼對應的內容
     * @param request 請求對象
     * @param errorCode
     * @return String
     * @since 2013年5月6日  
    */
    String getErrorMsg(HttpServletRequest request, String errorCode);
    /**
     * 按國際化方式獲取錯誤碼對應的內容,並處理參數
     * @param request 請求對象
     * @param errorCode
     * @param arg
     * @return String
     * @since 2013年5月6日  
    */ 
    String getErrorMsg(HttpServletRequest request, String errorCode, Object[] arg);
}