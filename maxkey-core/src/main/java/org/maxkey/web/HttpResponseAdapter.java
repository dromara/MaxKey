package org.maxkey.web;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.constants.ContentType;
import org.springframework.stereotype.Component;

@Component
public class HttpResponseAdapter {

    
    public void setContentType(
                        HttpServletResponse response,
                        String format) {
        
        if(format == null || format.equalsIgnoreCase("") || format.equalsIgnoreCase(ResponseConstants.FORMAT_TYPE.XML)) {
            response.setContentType(ContentType.APPLICATION_XML_UTF8);
        }else {
            response.setContentType(ContentType.APPLICATION_JSON_UTF8);
        }
    }
    
	public void write(HttpServletResponse response,String content, String format) {

		setContentType(response , format);
		
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        
        ServletOutputStream out = null;
            try {
				out = response.getOutputStream();
            	// write the data out
                out.write(content.getBytes());
                out.flush();
            }catch (IOException e) {
				e.printStackTrace();
			} finally {
                try {
                	if(out != null) {
                		out.close();
                	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	}
    
}
