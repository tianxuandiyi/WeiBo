/**
 *SharedPreferencesUtil.java
 *2011-9-13 下午10:38:06
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.droidstouch.iweibo.bean.UserInfo;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class SharedPreferencesUtil
{

	
	
	private static final String LOGIN_USER="login_user";

	
	
	/**
	 * 保存登录用户信息
	 * @param context
	 * @param user
	 */
	public static void saveLoginUser(Context context,UserInfo user)
	{
		
		
		SharedPreferences sp = context.getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
		
		 Editor editor =	sp.edit();
			
		 editor.putString(UserInfo.USER_ID, user.getUserId());
		 editor.putString(UserInfo.USER_NAME, user.getUserName());
		 editor.putString(UserInfo.TOKEN, user.getToken());
		 editor.putString(UserInfo.TOKEN_SECRET, user.getTokenSecret());
			
		 editor.commit();
		
	}
	
	
	/**
	 * 获取登录用户信息
	 * @param context
	 * @return
	 */
	public static UserInfo getLoginUser(Context context)
	{
		
		SharedPreferences sp = context.getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
		String userId=sp.getString(UserInfo.USER_ID, "");
		String userName=sp.getString(UserInfo.USER_NAME, "");
		String token=sp.getString(UserInfo.TOKEN, "");
		String tokenSecret=sp.getString(UserInfo.TOKEN_SECRET, "");
		
		if("".equals(userId))
			return null;
		
		return new UserInfo(userId, userName, token, tokenSecret, "1");
	}
	
	
}
