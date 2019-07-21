package org.maxkey.web.contorller;

import java.util.ArrayList;
import java.util.List;

import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnUserToken;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnUserTokenService;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/socialsignon"})
public class SocialSignOnListController {
	final static Logger _logger = LoggerFactory.getLogger(SocialSignOnListController.class);
	
	@Autowired
	SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	protected SocialSignOnUserTokenService socialSignOnUserService;
	
	@Autowired
  	@Qualifier("applicationConfig")
  	protected ApplicationConfig applicationConfig;
	
	@RequestMapping(value = { "/list" })
	public ModelAndView forwardUpdate() {
		
		ModelAndView modelAndView=new ModelAndView("social/socialSignOnProvider");
		if(applicationConfig.getLoginConfig().isSocialSignOn()){
			List<SocialSignOnProvider>  listSocialSignOnProvider= socialSignOnProviderService.getSocialSignOnProviders();
			
			SocialSignOnUserToken socialSignOnUser=new SocialSignOnUserToken();
			socialSignOnUser.setUid(WebContext.getUserInfo().getId());
			List<SocialSignOnUserToken>  listSocialSignOnUserToken= socialSignOnUserService.query(socialSignOnUser);
			List<SocialSignOnProvider>  listBindSocialSignOnProvider=new ArrayList<SocialSignOnProvider>();
			_logger.debug("list SocialSignOnProvider : "+listSocialSignOnProvider);
			_logger.debug("list SocialSignOnUserToken : "+listSocialSignOnUserToken);
			for (SocialSignOnProvider ssop : listSocialSignOnProvider){
				SocialSignOnProvider socialSignOnProvider=new SocialSignOnProvider();
				socialSignOnProvider.setProvider(ssop.getProvider());
				socialSignOnProvider.setProviderName(ssop.getProviderName());
				socialSignOnProvider.setIcon(ssop.getIcon());
				for(SocialSignOnUserToken ssout :listSocialSignOnUserToken){
					if(ssout.getProvider().equals(ssop.getProvider())){
						socialSignOnProvider.setUserBind(true);
						_logger.debug("binded provider : "+ssout.getProvider());
					}
				}
				listBindSocialSignOnProvider.add(socialSignOnProvider);
			}
			
			if(listBindSocialSignOnProvider.size()%4>0){
				for(int i=0;i<=listBindSocialSignOnProvider.size()%4;i++){
					listBindSocialSignOnProvider.add(new SocialSignOnProvider());
				}
				
			}
			
			modelAndView.addObject("listSocialSignOnProvider", listBindSocialSignOnProvider);
		}
		return modelAndView;
	}
	
}
