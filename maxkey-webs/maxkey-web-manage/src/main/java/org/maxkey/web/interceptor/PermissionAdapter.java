package org.maxkey.web.interceptor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.config.ApplicationConfig;
import org.maxkey.dao.service.NavigationsService;
import org.maxkey.domain.Navigations;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 权限Interceptor处理
 * 权限处理需在servlet.xml中配置
 *  mvc:interceptors  permission
 * @author Crystal.Sea
 *
 */

public class PermissionAdapter extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(PermissionAdapter.class);
	//无需Interceptor url
	@Autowired
	@Qualifier("applicationConfig")
	private ApplicationConfig applicationConfig;
	
	static ConcurrentHashMap<String ,String >navigationsMap=null;
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.debug("PermissionAdapter preHandle");
		 //加载定义的功能菜单地址
		if(navigationsMap==null){
			List<Navigations>   navigationsList=((NavigationsService)WebContext.getBean("navigationsService")).query(null);
			navigationsMap=new ConcurrentHashMap<String ,String >();
			for(Navigations nav : navigationsList){
				if(nav.getUrl()==null)continue;
				if(nav.getUrl().endsWith("/")){
					navigationsMap.put("/"+nav.getUrl(), nav.getId());
				}else{
					navigationsMap.put("/"+nav.getUrl()+"/", nav.getId());
				}
			}
			_logger.debug("navigationsMap : "+navigationsMap);
		}
		
		UserInfo userInfo =WebContext.getUserInfo();//取得登录用户
		
		if(userInfo==null){//判断用户和角色，判断用户是否登录用户
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
			dispatcher.forward(request, response);
			return false;
		}
		
		//取得当前访问地址 Access URL
		String accessURI=request.getRequestURI().substring(request.getContextPath().length());
		if(!accessURI.endsWith("/")){
			accessURI=accessURI+"/";
		}
		//定义匿名可以访问URL地址
		if(applicationConfig.getAnonymousAccessUrls().containsKey(accessURI)){
			_logger.debug("Access URI : "+accessURI+" , AnonymousAccessUrls .");
			return true;
		}
		
		boolean hasNavAccess=true;

		
		/*	
		boolean preHandler = super.preHandle(request, response, handler);
		
		if(preHandler) {
			preHandler = false;
			
			
			if(!preHandler){//无权限转向
				log.debug("You do not have permission to access "+accessUrl);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/accessdeny");
				dispatcher.forward(request, response);
				return false;
			}
		}*/
		return true;
	}
}
