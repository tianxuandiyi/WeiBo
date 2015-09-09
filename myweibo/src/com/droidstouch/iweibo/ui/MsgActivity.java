/**
 *HomeActivity.java
 *2011-9-18 下午10:00:06
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.ui;

import com.droidstouch.iweibo.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class MsgActivity extends Activity
{


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.msg);
		System.out.println("msg.....");
	}
	
}
