/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package com.google.code.kaptcha.impl;

import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

public class UniqueTextCreator  extends Configurable implements TextProducer{
	Random rand = new Random();
	
	@Override
	public String getText() {
		int length = getConfig().getTextProducerCharLength();
		char[] chars = getConfig().getTextProducerCharString();
		StringBuffer text = new StringBuffer();
		int i = 0;
		while ( i < length){
			char word= chars[rand.nextInt(chars.length)];
			if(text.indexOf(word + "") <= -1 ) {
				text.append(word);
				i++;
			}
		}
		return text.toString();
	}

}
