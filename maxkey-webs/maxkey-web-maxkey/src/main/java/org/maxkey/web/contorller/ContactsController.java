package org.maxkey.web.contorller;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.DateUtils;
import org.maxkey.util.RQCodeUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.endpoint.ImageEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/contact"})
public class ContactsController {
	final static Logger _logger = LoggerFactory.getLogger(ContactsController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value={"/list"})
	public ModelAndView forward(){
		return new ModelAndView("contact/contactList");
	}
	
	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<UserInfo> forwardUserInfoList(@ModelAttribute("userinfo") UserInfo userinfo){
		return userInfoService.queryPageResults(userinfo);
		
	}
	
	@RequestMapping(value={"/details/{id}"})
	public ModelAndView details(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("contact/details");
		UserInfo userInfo=new UserInfo();
		userInfo.setId(id);
		userInfo=userInfoService.load(userInfo);
		WebContext.getSession().setAttribute(userInfo.getId(), userInfo.getPicture());
		modelAndView.addObject("rqcode", RQVCard(userInfo));
		modelAndView.addObject("model", userInfo);
		return modelAndView;
	}
	
	/*
	 * BEGIN:VCARD
	 * VERSION:3.0
	 * N:Gump;Forrest;;Mr.
	 * FN:Forrest Gump
	 * ORG:Bubba Gump Shrimp Co.
	 * TITLE:Shrimp Man
	 * PHOTO;VALUE=URL;TYPE=GIF:http://www.example.com/dir_photos/my_photo.gif
	 * TEL;TYPE=WORK,VOICE:(111) 555-12121
	 * TEL;TYPE=HOME,VOICE:(404) 555-1212
	 * ADR;TYPE=WORK:;;100 Waters Edge;Baytown;LA;30314;United States of America
	 * LABEL;TYPE=WORK:100 Waters Edge\nBaytown, LA 30314\nUnited States of America
	 * ADR;TYPE=HOME:;;42 Plantation St.;Baytown;LA;30314;United States of America
	 * LABEL;TYPE=HOME:42 Plantation St.\nBaytown, LA 30314\nUnited States of America
	 * EMAIL;TYPE=PREF,INTERNET:forrestgump@example.com
	 * REV:2008-04-24T19:52:43Z
	 * END:VCARD



	 * BEGIN:VCARD
	 * VERSION:4.0
	 * N:Gump;Forrest;;;
	 * FN:Forrest Gump
	 * ORG:Bubba Gump Shrimp Co.
	 * TITLE:Shrimp Man
	 * PHOTO;MEDIATYPE=image/gif:http://www.example.com/dir_photos/my_photo.gif
	 * TEL;TYPE=work,voice;VALUE=uri:tel:+1-111-555-1212
	 * TEL;TYPE=home,voice;VALUE=uri:tel:+1-404-555-1212
	 * ADR;TYPE=work;LABEL="100 Waters Edge\nBaytown, LA 30314\nUnited States of America"
	 *   :;;100 Waters Edge;Baytown;LA;30314;United States of America
	 * ADR;TYPE=home;LABEL="42 Plantation St.\nBaytown, LA 30314\nUnited States of America"
	 *  :;;42 Plantation St.;Baytown;LA;30314;United States of America
	 * EMAIL:forrestgump@example.com
	 * REV:20080424T195243Z
	 * END:VCARD
	 */
	public String RQVCard(UserInfo userInfo){
		String strRQVCard = 
				 "BEGIN:VCARD\n" +
	        	 "VERSION:3.0\n" +
	        	 "N:"+userInfo.getDisplayName()+"\n" +
	        	 "ORG:"+userInfo.getOrganization()+"\n" +
	        	 "TITLE:"+userInfo.getJobTitle()+"\n" +
	        	 "TEL;TYPE=WORK,VOICE:"+userInfo.getMobile()+"\n" +
	        	 "ADR;TYPE=WORK:"+userInfo.getWorkStreetAddress()+"\n" +
	        	 "EMAIL:"+userInfo.getEmail()+"\n" +
	        	 "URL:"+userInfo.getWebSite()+"\n" +
	        	 "GENDER:"+(userInfo.getGender()==1?"F":"M")+"\n" +
	        	 "REV:"+DateUtils.toUtc(new Date())+"\n" +
	        	 "END:VCARD\n" ;
		_logger.debug("RQVCard : \n"+strRQVCard);
		BufferedImage bufferedImage;
		try {
			bufferedImage = RQCodeUtils.write2BufferedImage(new String(strRQVCard.getBytes("UTF-8"),"iso-8859-1"), "gif", 300, 300);
			byte[] imageByte=ImageEndpoint.bufferedImage2Byte(bufferedImage);
			String uuid=UUID.randomUUID().toString().toLowerCase();
			WebContext.getSession().setAttribute(uuid, imageByte);
			return uuid;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
