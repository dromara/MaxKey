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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Contorller调用完成后进行日志操作
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

    /**
     *  after the handler is executed.
     */
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, 
            Object handler,ModelAndView modelAndView) throws Exception {
        _logger.debug("postHandle");
        Message message = WebContext.getMessage();//读取session中message

        if (message != null) {
            //判断message类型
            if (message.getMessageScope() == MessageScope.DB
                    || message.getMessageScope() == MessageScope.DB_CLIENT) {
                UserInfo userInfo = WebContext.getUserInfo();//取得当前用户信息

                //创建日志记录
                HistoryLogs historyLogs = new HistoryLogs(
                        message.getServiceName(),
                        message.getCode(),
                        message.getMessage(),
                        JsonUtils.object2Json(message.getMessageObject()),
                        message.getMessageType().toString(),
                        message.getOperateType().toString(),
                        userInfo == null ? null : userInfo.getId(),
                                userInfo == null ? null : userInfo.getUsername(),
                                        ""
                        );
                _logger.debug("insert db historyLogs content : " + historyLogs);
                historyLogsService.insert(historyLogs);//日志插入数据库
                //message类型仅插入数据库
                if (message.getMessageScope() == MessageScope.DB) {
                    WebContext.clearMessage();//清除message
                }
            }
        }
    }
}
