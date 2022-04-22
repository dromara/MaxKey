package com.google.code.kaptcha.impl;

import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

public class UniqueTextCreator  extends Configurable implements TextProducer{

	@Override
	public String getText() {
		int length = getConfig().getTextProducerCharLength();
		char[] chars = getConfig().getTextProducerCharString();
		Random rand = new Random();
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
