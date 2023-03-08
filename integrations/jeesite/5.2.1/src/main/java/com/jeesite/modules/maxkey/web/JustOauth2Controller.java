package com.jeesite.modules.maxkey.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeesite.common.config.Global;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.maxkey.base.IBaseJustOauth2Controller;
import com.jeesite.modules.maxkey.utils.Oauth2UserLoginUtils;

import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

/**
 * 推荐参考案例https://github.com/justauth/JustAuth-demo/blob/master/src/main/java/me/zhyd/justauth/RestAuthController.java
 * JustAuth 控制层
 * 
 * @author 长春叭哥
 * @version 2023-02-23
 */
@Controller
@RequestMapping({ "/oauth2" })
public class JustOauth2Controller extends BaseController implements IBaseJustOauth2Controller {

    @Override
    @RequestMapping({"/login/{source}"})
    public String login(String source, HttpServletRequest request) {
	// TODO Auto-generated method stub
	logger.debug(source);
	return "redirect:" + Oauth2UserLoginUtils.getAuthRequest(source).authorize((request.getParameter("state") == null ? AuthStateUtils.createState() : request.getParameter("state")));
    }

    @Override
    @RequestMapping({"/callback/{source}"})
    public String callback(String source, AuthCallback callback, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	logger.debug(source);

	AuthRequest authRequest = Oauth2UserLoginUtils.getAuthRequest(source);
	AuthResponse<?> rauthResponse = authRequest.login(callback);
	if(rauthResponse.getData() instanceof AuthUser) {
	    AuthUser authUser = (AuthUser) rauthResponse.getData();
	    //处理相关的绑定业务，该处仅做简单集成与演示专用。
	    logger.debug("authUser:"+JsonMapper.toJson(authUser));
	    Oauth2UserLoginUtils.loginByOauthUserId(authUser.getUsername());
	    return renderResult(Global.TRUE, text("回调信息获取成功！"));
	} else {
	    return null;
	}
	
    }

    @Override
    @PostMapping({"/binder"})
    @ResponseBody
    public String binder(String id, String username, String password, String validCode, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	logger.debug(id, username);
	return null;
    }

    @Override
    @RequestMapping({"/unbind"})
    @ResponseBody
    public String unbind(String id, HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
	logger.debug(id);
	return null;
    }

}
