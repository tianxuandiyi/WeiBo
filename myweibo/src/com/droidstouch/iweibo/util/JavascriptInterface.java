package com.droidstouch.iweibo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavascriptInterface
{
	
	
	
	//授权码
	public static String PIN;
	
	
	/**
	 * 获取PIN值
	 * @param html
	 */
	public void getPin(String html)
	{
		String pin=null;
		
		String reg="[0-9]{6}";
		
		Pattern pattern =Pattern.compile(reg);
		
		Matcher matcher =pattern.matcher(html);
		
		if(matcher.find())
		{
			pin=matcher.group(0);
		}
		System.out.println(html);
		System.out.println("getPin!!!!!!!!  " + pin);
		PIN = pin;
	}

}
