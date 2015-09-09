/**
 *NetUtil.java
 *2011-9-13 下午09:35:08
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.drawable.Drawable;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class NetUtil
{

	
	
	public static Drawable getNetImage(URL url)
	{
		
		if(null ==url)
		 return null;
		
		try
		{
			HttpURLConnection connection =(HttpURLConnection) url.openConnection();
			
			return Drawable.createFromStream(connection.getInputStream(), "image");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
