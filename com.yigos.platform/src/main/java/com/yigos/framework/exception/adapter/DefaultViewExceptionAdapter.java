package com.yigos.framework.exception.adapter;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import com.yigos.framework.common.util.mapping.ConfigMapping;
import com.yigos.framework.common.util.datetime.DateTimeUtil;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.model.JsonModel;
import com.yigos.framework.common.util.JsonUtils;
import com.yigos.framework.exception.BizException;
import com.yigos.framework.exception.CustomException;
import com.yigos.framework.exception.msg.ErrorMsgSrv;
import com.yigos.framework.exception.msg.ExceptionConstants;
import com.yigos.framework.exception.msg.ErrorInfo;

/**
 * 异常处理
 */
@Component
public class DefaultViewExceptionAdapter implements ViewExceptionAdapter {
	private static Logger logger = Logger.getLogger(DefaultViewExceptionAdapter.class);
	@Autowired
	private ErrorMsgSrv errorAccessor;
	private final ModelAndView modelAndView = new ModelAndView();

	@Override
	public ModelAndView createModelAndView(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String isShowLogCode = ConfigMapping.getProperty("show.log.code");
		String logCode = DateTimeUtil.getSysTime();
		if (ex instanceof CustomException) {
			return handleBizException(request, response, handler, ex, isShowLogCode, logCode);
		} else {
			return handleGenericException(request, response, handler, ex, isShowLogCode, logCode);
		}
	}

	private ModelAndView handleBizException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex, 
			String isShowLogCode, String logCode)
			throws Exception {
		BizException bizException = (BizException) ex;
		String errorCode = bizException.getErrorCodeString();
		String errorType = bizException.getErrorType();// 可以傳，可以不傳
		if (errorType != null && errorType.equals(ExceptionConstants.SYS_ERROR)) {
			// 如果是系統異常，不需要提供錯誤碼
			return handleGenericException(request, response, handler, ex, isShowLogCode, logCode);
		}
		Exception exception = bizException.getException();
		if (exception != null && exception instanceof BizException) {
			return handleBizException(request, response, handler, exception, isShowLogCode, logCode);
		}
		String errorMsg = errorAccessor.getErrorMsg(request, errorCode,
				bizException.getArg());
		if (errorMsg == null || errorMsg.trim().length() == 0) {
			errorMsg = errorAccessor.getErrorMsg(request,
					ExceptionConstants.UNKNOWN_ERROR);
		}
		if (isShowLogCode.equalsIgnoreCase("Y")) {
			errorMsg = new StringBuffer(logCode).append("::").append(errorMsg)
					.toString();
		}
		if (errorType == null || errorType.equals(ExceptionConstants.BIZ_ERROR)) {
			logger.error("業務異常。");
		} else if (errorType.equals(ExceptionConstants.DB_ERROR)) {
			logger.error("數據庫異常。");
		} else if (errorType.equals(ExceptionConstants.UNKNOWN_ERROR)) {
			logger.error("未知錯誤。");
		}
		logger.error(errorMsg);
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		logger.error(logCode + "=" + ex.getMessage(), ex);
		// 打印root異常
		if (rootCause != null)
			logger.error(rootCause.getMessage(), rootCause);
		// 記錄詳細信息
		if (exception != null) {// 自己構造的業務異常exception可以是null
			logger.error(exception);
		}
		return messView(request, response, handler, ex, isShowLogCode, logCode, errorMsg);
	}

	private ModelAndView handleGenericException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex, 
			String isShowLogCode, String logCode)
			throws Exception {
		// 針對運行時異常的處理方式：在當前介面顯示提示信息
		String errorMsg = "";
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		logger.error("運行時異常。");
		logger.error(logCode + "=" + ex.getMessage(), ex);
		if (rootCause != null) {
			// 如果識別出來異常類型，轉到對應的處理邏輯
			if (rootCause instanceof PersistenceException
					|| rootCause instanceof HibernateException
					|| rootCause instanceof DataAccessException
					|| rootCause instanceof SQLException) {
				errorMsg = "";
			} else if (ex instanceof PersistenceException) {// PersistenceException是一個大類
				errorMsg = getPersistenceExceptionMsg(request, ex);
			} else if (rootCause instanceof ConstraintViolationException) {
				errorMsg = getConstraintViolationExceptionMsg(request, ex);
			} else if (rootCause instanceof BizException) {
				// 如果有根源異常還是業務異常，將根源異常拋出來
				handleBizException(request, response, handler,
						(BizException) rootCause, isShowLogCode, logCode);
				return modelAndView;
			}
			return messView(request, response, handler, ex, isShowLogCode, logCode, errorMsg);
		}
		if (ex instanceof NullPointerException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_099);// 空指針異常
		} else if (ex instanceof IndexOutOfBoundsException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_098);// 下标越界異常
		} else if (ex instanceof ClassCastException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_097);// 類型強制轉換異常
		} else if (ex instanceof EOFException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_096);// 文件已結束異常
		} else if (ex instanceof FileNotFoundException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_095);// 文件未找到異常
		} else if (ex instanceof NumberFormatException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_094);// 字串轉換為數位異常
		} else if (ex instanceof IOException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_093);// 輸入輸出異常
		} else if (ex instanceof NoSuchMethodException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_092);// 方法未找到異常
		} else if (ex instanceof ClassNotFoundException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_091);// 找不到類異常
		} else if (ex instanceof ArithmeticException) {
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.ERROR_CODE_090);// 算術條件異常
		} else {// 显示提示信息 "錯誤碼：001 系統異常，請聯繫系統管理員"
			errorMsg = errorAccessor.getErrorMsg(request, ExceptionConstants.SYS_ERROR);
		}
		return messView(request, response, handler, ex, isShowLogCode, logCode, errorMsg);
	}

	private ModelAndView messView(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex,
			String isShowLogCode, String logCode, String errorMsg)
			throws Exception {
		if (isShowLogCode.equalsIgnoreCase("Y")) {
			errorMsg = new StringBuffer(logCode).append("::").append(errorMsg)
					.toString();
		}
		// /skyno/manager/user/181/edit.json
		String requestURI = request.getRequestURI();
		if (requestURI.endsWith(".json")) {
			// 按照json方式來處理
			return jsonView(request, response, handler, ex, errorMsg);
		} else {
			return jspView(request, response, handler, ex, errorMsg);
		}
	}

	private String getConstraintViolationExceptionMsg(
			HttpServletRequest request, Exception ex) {
		Throwable cause = ex.getCause();
		if (cause != null && cause instanceof RollbackException) {
			Throwable dbCause = cause.getCause();
			if(dbCause !=null && 
			dbCause instanceof javax.validation.ConstraintViolationException){
				return dbCause.getMessage();
			}
			return cause.getMessage();
		}
		// 显示提示信息
		return errorAccessor.getErrorMsg(request, ExceptionConstants.DB_ERROR);
	}

	private String getPersistenceExceptionMsg(HttpServletRequest request,
			Exception ex) {
		Throwable cause = ex.getCause();
		if (cause != null && cause instanceof JDBCConnectionException) {// 數據庫連接異常
			return errorAccessor.getErrorMsg(request,
					ExceptionConstants.ERROR_CODE_088);
		}
		// 显示提示信息
		return errorAccessor.getErrorMsg(request, ExceptionConstants.DB_ERROR);
	}

	private ModelAndView jsonView(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex,
			String errorMsg) throws Exception {
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().write(
				JsonUtils.toJsonString(new JsonModel(false, DataDict.ERROR, errorMsg, 0)));
		return modelAndView;
	}

	private ModelAndView jspView(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex,
			String errorMsg) throws Exception {
		ErrorInfo info = new ErrorInfo();
		info.setMsgTitle(errorAccessor.getErrorMsg(request,
				ExceptionConstants.PROMPT_STRING));
		info.setbAlert(true);
		info.setAlertInfo(errorMsg);
		request.setAttribute(DataDict.ERROR_INFO, info);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/custom/jsp/error/exception.jsp");
		dispatcher.forward(request, response);
		return modelAndView;
	}
}
