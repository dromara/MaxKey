package org.maxkey.web.endpoint;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = "/image")
public class ImageEndpoint {


	/**
	 * captcha image Producer
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/{id}")
    public ModelAndView imageHandleRequest(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") String id) throws Exception {
            // Set to expire far in the past.
            response.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            // return a jpeg/gif
            response.setContentType("image/gif");
            // create the text for the image
            byte[]image=(byte[]) request.getSession().getAttribute(id);
            request.getSession().removeAttribute(id);
            // create the image with the text
            if(image!=null){
	            InputStream in = new ByteArrayInputStream(image);
	            BufferedImage bi = ImageIO.read(in);
	            ServletOutputStream out = response.getOutputStream();
	            // write the data out
	            ImageIO.write(bi, "gif", out);
	            try{
                    out.flush();
	            }finally{
	                    out.close();
	            }
            }
            return null;
    }
}
