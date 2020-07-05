/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

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
