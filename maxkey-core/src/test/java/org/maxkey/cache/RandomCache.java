/**
 * 
 */
package org.maxkey.cache;

import java.util.Random;

import org.maxkey.cache.AbstractCache;

/**
 * @author amarsoft
 *
 */
public class RandomCache extends AbstractCache {
	java.util.Random random=new Random();
	
	int i;
	
	/**
	 * 
	 */
	public RandomCache() {
		// TODO Auto-generated constructor stub
		this.setInterval(5);
	}

	/**
	 * @param name
	 */
	public RandomCache(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.connsec.cache.AbstractCache#business()
	 */
	@Override
	public void business() {
		// TODO Auto-generated method stub
		i=random.nextInt(100);
		System.out.println(i);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
	

}
