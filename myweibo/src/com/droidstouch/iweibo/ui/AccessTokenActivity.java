package com.droidstouch.iweibo.ui;

import java.util.HashMap;
import java.util.Map;

import weibo4android.http.AccessToken;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.Task;
import com.droidstouch.iweibo.bean.UserInfo;
import com.droidstouch.iweibo.logic.MainService;
import com.droidstouch.iweibo.services.UserInfoServices;


/**
 * 获取用户授权信息
 * @author Touch Andorid
 *http://bbs.droidstouch.com
 */
public class AccessTokenActivity extends Activity implements IWeiboActivity
{

	
	
	//private String pin;

	private static final String TAG="AccessTokenActivity";
	
	private ProgressDialog pDialog;
	private UserInfoServices uServices;
	
	
	public static final int GET_TOKEN=1;
	
	public static final int GET_HEAD=2;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		
		init();
		Task task = new Task(Task.GET_ACCESS_TOKEN,null);
		MainService.newTask(task);
		
		MainService.addActivity(this);
	}



	@Override
	public void init()
	{
		if(null == pDialog)
		{
			 pDialog = new ProgressDialog(this);
		}
		
		pDialog.setMessage(getString(R.string.get_access_token_ing));
		pDialog.show();
		
		uServices = new UserInfoServices(this);
	}



	@Override
	public void refresh(Object... params)
	{
		
		int flag = (Integer) params[0];
		
		
		if(GET_TOKEN==flag)
		{
			
			pDialog.setMessage("正在获取用户头像...");
			
			AccessToken token = (AccessToken)params[1];
			
			UserInfo user = new UserInfo(token.getUserId()+"",token.getScreenName(),token.getToken(),token.getTokenSecret(),"1");
			if(uServices.getUserInfoByUserId(token.getUserId()+"")==null)
			{
				uServices.insertUserInfo(user);
				Log.i(TAG, "保存授权用户:" + user.getUserId());
			}
			else 
			{
				// 修改
				uServices.updateUserInfo(user);
				Log.i(TAG, "更新授权用户信息:" + user.getUserId());
			}
			
			Map<String, Object> taskParams = new HashMap<String, Object>(1);
			taskParams.put("user", user);
			
			Task  task = new Task(Task.GET_USER_HEAD, taskParams);
			MainService.newTask(task);
		}
		else if(GET_HEAD == flag)
		{
		
			pDialog.dismiss();
			
			UserInfo tempUser = (UserInfo) params[1];
			
			
			BitmapDrawable tempDrawable = (BitmapDrawable) tempUser.getUserIcon();
			
			System.out.println("tempDrawable== " + tempDrawable);
			uServices.updateUserInfo(tempUser.getUserId(), tempUser.getUserName(), tempDrawable.getBitmap());
			
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			
		}
		
		
		
		
		
	}
	
	
	
	
	
}
