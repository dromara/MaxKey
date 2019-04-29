/**
 * 
 */
package com.connsec.cache;

import java.util.ArrayList;

import org.maxkey.cache.AbstractCache;
import org.maxkey.cache.CacheFactory;

/**
 * @author amarsoft
 *
 */
public class CacheFactoryTest {

	/**
	 * 
	 */
	public CacheFactoryTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomCache randomCache=new RandomCache();
		ArrayList<AbstractCache> cacheList=new ArrayList<AbstractCache>();
		cacheList.add(randomCache);
		CacheFactory cacheFactory=new CacheFactory();
		cacheFactory.setCache(cacheList);
		cacheFactory.start();
	}

}
