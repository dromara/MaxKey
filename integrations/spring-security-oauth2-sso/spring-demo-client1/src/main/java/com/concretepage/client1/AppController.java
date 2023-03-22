package com.concretepage.client1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AppController {
	@GetMapping("hello")
	public ModelAndView welcome() {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("welcome");
	    return mav;
    }
	@GetMapping("error")
	public ModelAndView error() {
		Map<String, String> model = new HashMap<>();
	    ModelAndView mav = new ModelAndView();
		mav.setViewName("error");
	    return mav;
    }	
}
