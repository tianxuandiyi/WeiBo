package com.droidstouch.iweibo.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.droidstouch.iweibo.bean.Task;
import com.droidstouch.iweibo.bean.UserInfo;
import com.droidstouch.iweibo.ui.AccessTokenActivity;
import com.droidstouch.iweibo.ui.IWeiboActivity;
import com.droidstouch.iweibo.util.AuthUtil;
import com.droidstouch.iweibo.util.JavascriptInterface;
import com.droidstouch.iweibo.util.NetUtil;


/**
 * 系统主服务
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class MainService extends Service implements Runnable
{

	// 任务队列
	private static Queue<Task> tasks = new LinkedList<Task>();
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();
	
	//是否运行线程
	private boolean isRun;
	private static Weibo weibo;
	// 当前系统登录用户
	public static UserInfo nowUser;
	
	
	static
	{
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		
		weibo = new Weibo();
		
	}
	
	
	Handler handler = new Handler()
	{
		
		public void handleMessage(android.os.Message msg) 
		{
			switch (msg.what)
			{
			 case Task.WEIBO_LOGIN:// 用户登录
			 {
				//更新UI
				IWeiboActivity activity = (IWeiboActivity) getActivityByName("LoginActivity");
				activity.refresh(msg.obj);
				
				break;
			 }
			 
			 case Task.GET_ACCESS_TOKEN:// 获取用户授权信息
			 {
				 
				 IWeiboActivity activity = (IWeiboActivity) getActivityByName("AccessTokenActivity");
				 activity.refresh(AccessTokenActivity.GET_TOKEN,msg.obj);
				 break;
			 }
			 case Task.GET_AUTH_URL:// 获取授权地址
				{
					IWeiboActivity activity = (IWeiboActivity) getActivityByName("WebViewActivity");
					activity.refresh(msg.obj);
					break;
				}
			 
				// 获取用户头像和用户名称
			 case Task.GET_USER_HEAD:
			 {
				 IWeiboActivity activity = (IWeiboActivity) getActivityByName("AccessTokenActivity");
				 activity.refresh(AccessTokenActivity.GET_HEAD,msg.obj);
				 break;
			 }
			 case Task.WEIBO_FRIEDNS_TIMELINE:
			 {
				 
				 IWeiboActivity activity = (IWeiboActivity) getActivityByName("HomeActivity");
				 activity.refresh(msg.obj);
				 break;
			 }
			 
			default:
				break;
			}
				
		};
	};
	
	
	
	
	/**
	 * 添加一个Activity对象
	 * @param activity
	 */
	public static void addActivity(Activity activity)
	{
		appActivities.add(activity);
	}
	
	
	
	/**
	 * 添加任务到任务队列中
	 * @param t
	 */
	public static void newTask(Task t)
	{
		tasks.add(t);
	}
	
	
	/**
	 * 根据Activity 的Name 获取Activity对象吧
	 * @param name
	 * @return
	 */
	private Activity getActivityByName(String name)
	{
		
		if(!appActivities.isEmpty())
		{
			for (Activity activity : appActivities)
			{
				if(null != activity)
				{
					if(activity.getClass().getName().indexOf(name) >0)
					{
						return activity;
					}
				}
			}
		}
		
		return null;
		
	}
	
	
	
	@Override
	public void onCreate()
	{
		
		isRun = true;
		Thread thread = new Thread(this);
		thread.start();
		
		
		super.onCreate();
	}
	
	
	
	public void run()
	{
		
		while(isRun)
		{
			Task task = null;
			if(!tasks.isEmpty())
			{
				task = tasks.poll();// 执行完任务后把改任务从任务队列中移除
				if(null != task)
				{
					doTask(task);
				}
			}
			try{Thread.sleep(1000);}catch (Exception e) {			} 
			
		}
		
			
	}

	
	// 处理任务
	private void doTask(Task t)
	{
		Message msg = handler.obtainMessage();
		msg.what =t.getTaskId();
		
		switch (t.getTaskId())
		{
			case Task.WEIBO_LOGIN:
			{
				
				nowUser= (UserInfo) t.getTaskParams().get("user");
				weibo.setToken(nowUser.getToken(), nowUser.getTokenSecret());
				
				break;
			}
			
			case Task.GET_ACCESS_TOKEN:// 获取用户授权信息
			{
				String pin =null;
				while (null == pin)
				{
					pin = JavascriptInterface.PIN;
				}
				AccessToken accessToken=null;
				while(null ==accessToken)
				{
					accessToken=AuthUtil.getAccessToken(pin);
				}
				
				msg.obj = accessToken;
				break;
			}
			case Task.GET_AUTH_URL:// 获取授权地址
			{
				String url =AuthUtil.getAuthorizationURL();
				msg.obj = url;
				break;
			}
			
			//获取用户头像和名称
			case Task.GET_USER_HEAD:
			{
				
				try
				{
					UserInfo userInfo = (UserInfo) t.getTaskParams().get("user");
					
					weibo.setToken(userInfo.getToken(), userInfo.getTokenSecret());
					
					User user=weibo.showUser(userInfo.getUserId());
					
					Drawable userIcon = NetUtil.getNetImage(user.getProfileImageURL());
					userInfo.setUserName(user.getName());
					userInfo.setUserIcon(userIcon);
					
					msg.obj = userInfo;
				} catch (WeiboException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			//获取当前登录用户及其所关注用户的最新微博消息
			case Task.WEIBO_FRIEDNS_TIMELINE:
			{
				
				try
				{
					Paging page = new Paging(1, 20);
					List<Status> status= weibo.getFriendsTimeline(page);
					msg.obj = status;
					
				} catch (WeiboException e)
				{
					e.printStackTrace();
				}
				
				break;
			}
			
		default:
			break;
		}
		
		handler.sendMessage(msg);
		
	}
	
	
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

}
