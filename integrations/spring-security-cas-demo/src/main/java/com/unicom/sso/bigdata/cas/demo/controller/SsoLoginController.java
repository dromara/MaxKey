package com.unicom.sso.bigdata.cas.demo.controller;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 控制类
 * @author baihz10
 * @date 2023/7/7 15:44
 */
@Controller
public class SsoLoginController {

    @GetMapping("/caslogin")
    public String home(Model model,HttpServletRequest request) {
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

        String username= assertion.getPrincipal().getName();
        username = Optional.ofNullable(username).orElse("anonymous");
        model.addAttribute("username", username);
        return "cas/login";
    }
}
