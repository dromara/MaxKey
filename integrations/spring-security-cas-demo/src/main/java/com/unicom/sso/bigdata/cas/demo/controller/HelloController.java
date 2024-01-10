package com.unicom.sso.bigdata.cas.demo.controller;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * cas 回调地址
 * @author baihz10
 * @date 2023/7/7 15:44
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String home(Model model, HttpServletRequest request) {
//        String token =request.getParameter("token");
//        System.out.println("token : "+token);
//        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
//
//        String username= assertion.getPrincipal().getName();
//        System.out.println("cas user:"+username);
//
//        username = Optional.ofNullable(username).orElse("anonymous");
//        Map<String, Object> attributes = Optional.ofNullable(assertion.getPrincipal().getAttributes()).orElse(new HashMap<>());
//
//        model.addAttribute("username", username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户信息：" + auth.getPrincipal());
        return "home";
    }

    @GetMapping("/hello2")
    public String home2(Model model, HttpServletRequest request) {
        String token =request.getParameter("token");
        System.out.println("token : "+token);
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

        String username= assertion.getPrincipal().getName();
        System.out.println("cas user:"+username);

        username = Optional.ofNullable(username).orElse("anonymous");
        Map<String, Object> attributes = Optional.ofNullable(assertion.getPrincipal().getAttributes()).orElse(new HashMap<>());

        model.addAttribute("username", username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户信息：" + auth.getPrincipal());
        return "home";
    }


}
