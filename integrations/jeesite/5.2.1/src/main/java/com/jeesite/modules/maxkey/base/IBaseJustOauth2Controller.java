package com.jeesite.modules.maxkey.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.zhyd.oauth.model.AuthCallback;


/**
 * 第三方授权登录 商业级版本功能实现咨询QQJeeSite彩虹：1766571055
 * @author 长春叭哥
 * @version 2020-02-23
 */
public interface IBaseJustOauth2Controller {

    /**
     *  Oauth2 登录
     * @param source
     * @param request
     * @return
     */
    public String login(@PathVariable("source") String source, HttpServletRequest request);

    /**
     * 回调地址
     * @param source
     * @param callback
     * @param redirectAttributes
     * @param model
     * @param request
     * @param response
     * @return
     */
    public String callback(@PathVariable("source") String source, AuthCallback callback, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response);

    /**
     * 绑定用户
     * @param id
     * @param username
     * @param password
     * @param validCode
     * @param request
     * @param response
     * @return
     */
    public String binder(String id, String username, String password, String validCode, HttpServletRequest request, HttpServletResponse response);

    /**
     * 解绑用户
     * @param id
     * @param request
     * @param response
     * @return
     */
    public String unbind(String id, HttpServletRequest request, HttpServletResponse response) ;
    
}
