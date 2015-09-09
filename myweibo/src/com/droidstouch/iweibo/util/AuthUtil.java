package com.droidstouch.iweibo.util;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;



/**
 * 用户OAuth授权工具类
 * @author Touch Android
 * http://bbs.droidstouch.com
 */
public class AuthUtil
{

	
	
	private static Weibo weibo;
	private static RequestToken requestToken ;
	static
	{
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		
		weibo = new Weibo();
		
	}
	
	
	
	/**
	 * 获取授权URL
	 * @return String
	 */
	public static  String getAuthorizationURL()
	{
		
		try
		{
			 requestToken = weibo.getOAuthRequestToken();
			
			return requestToken.getAuthorizationURL();
		} catch (WeiboException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/**
	 * 获取AccessToken信息
	 * @param pin
	 * @return
	 */
	public static AccessToken getAccessToken(String pin)
	{
		try
		{
			AccessToken accessToken =requestToken.getAccessToken(pin);
			
			System.out.println("Access token: "+ accessToken.getToken());
			System.out.println("Access token secret: "+ accessToken.getTokenSecret());
			System.out.println("Access userID: "+accessToken.getUserId());
			System.out.println("Access userName: "+accessToken.getScreenName());
			
			return accessToken;
		} catch (WeiboException e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	
	
}
