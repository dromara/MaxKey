package org.maxkey.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.dao.service.HistoryLogsService;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Contorller调用完成后进行日志操作
 * 
 * 日志处理需在parasec-servlet.xml中配置
 * mvc:interceptors  log
 * @author Crystal.Sea
 *
 */
@Component
public class HistoryLogsAdapter extends HandlerInterceptorAdapter {
	
	private static final Logger _logger = LoggerFactory.getLogger(HistoryLogsAdapter.class);
	
	 @Autowired
	 @Qualifier("historyLogsService")
	 private HistoryLogsService historyLogsService;
	
	// after the handler is executed
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		Message message = WebContext.getMessage();//读取session中message
		
		if(message != null){
			if(message.getMessageScope() == MessageScope.DB || message.getMessageScope() == MessageScope.DB_CLIENT) {//判断message类型
				UserInfo userInfo =WebContext.getUserInfo();//取得当前用户信息
				//创建日志记录
				HistoryLogs logs = new HistoryLogs(
					message.getServiceName(),
					message.getCode(),
					message.getMessage(),
					JsonUtils.object2Json(message.getMessageObject()),
					message.getMessageType().toString(),
					message.getOperateType().toString(),
					userInfo==null?null:userInfo.getId(),
					userInfo==null?null:userInfo.getUsername(),
					""
				);
				_logger.debug("insert db logs content : "+logs);
				historyLogsService.insert(logs);//日志插入数据库
				if(message.getMessageScope() == MessageScope.DB) {//message类型仅插入数据库
					WebContext.clearMessage();//清除message
				}
			}
		}
	}
}
