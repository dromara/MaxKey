/**
 * 
 */
package org.maxkey.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal
 * @version 1.0
 * @Date  2015/11/12
 */
public  abstract class AbstractCache extends Thread {

	protected final static Logger _logger = LoggerFactory.getLogger(AbstractCache.class);
	
	private int interval	=	30000/1000;
	
	/**
	 * 
	 */
	public AbstractCache() {

	}

	public abstract void business();
	
	
	 @Override
     public void run() {
		 while(true){
			 _logger.debug("Cache Thread Start run "+getName());
			 _logger.info("Cache Thread Start run "+this.getClass());
	         try {
	        	 
	        	 business();
	        	 
	        	 _logger.debug("Cache Thread "+getName()+" Finished . ");
		         _logger.info("Cache Thread "+this.getClass()+" Finished . ");
		         
	        	 _logger.debug("Cache Thread sleep "+(interval * 1000)+" minute . ");
	             sleep(interval * 1000);
	         } catch (InterruptedException e) {
	        	 _logger.error(e.getMessage(), e);
	         }
	         
		 }
     }
	 
	@Override
	public void start(){
		this.run();
	}
	
	
	/**
	 * @param name
	 */
	public AbstractCache(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
