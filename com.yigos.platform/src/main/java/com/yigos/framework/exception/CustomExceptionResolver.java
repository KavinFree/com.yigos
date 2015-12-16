package com.yigos.framework.exception;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.yigos.framework.common.util.datetime.DateTimeUtil;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.model.JsonModel;
import com.yigos.framework.common.util.JsonUtils;
import com.yigos.framework.exception.adapter.ViewExceptionAdapter;
import com.yigos.framework.exception.msg.ErrorInfo;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = Logger.getLogger(CustomExceptionResolver.class);
	@Autowired
	private ViewExceptionAdapter viewExceptionAdapter;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		try {
			return viewExceptionAdapter.createModelAndView(request, response,
					handler, ex);
		} catch (Exception e) {
			String logCode = DateTimeUtil.getSysTime();
			logger.error("未捕捉到的異常");
            logger.error(logCode + "=" + e.getMessage(), e);
			String requestURI = request.getRequestURI(); // /skyno/manager/user/181/edit.json
			if (requestURI.endsWith(".json")) {// 處理json請求
				response.setContentType("text/plain;charset=utf-8");
				try {
					response.getWriter().write(
							JsonUtils.toJsonString(new JsonModel(false,
									DataDict.ERROR, "UNKNOWN ERROR", 0)));
				} catch (Exception ex1) {
					logger.error(logCode + "=" + ex1.getMessage(), ex1);
				}
				return new ModelAndView();
			} else {
				// 這裡不做多語是為了保證catch裏面的語句不再產生異常
				ErrorInfo info = new ErrorInfo();
				if (e instanceof NoSuchMessageException) {
					info.setMsgTitle("提示資訊");
					info.setAlertInfo("::獲取多語言消息的時候出錯，可能原因：語言編碼沒有定義");
				} else {
					info.setMsgTitle("Info");
					info.setAlertInfo("UNKNOWN ERROR");
				}
				info.setbAlert(true);
				request.setAttribute(DataDict.ERROR_INFO, info);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/custom/jsp/error/exception.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (ServletException e1) {
					logger.error(logCode + "=" + e1.getMessage(), e1);
				} catch (IOException ioe) {
					logger.error(logCode + "=" + ioe.getMessage(), ioe);
				}
				return new ModelAndView();
			}
		}
	}

}