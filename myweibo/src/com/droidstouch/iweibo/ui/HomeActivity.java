/**
 *HomeActivity.java
 *2011-9-18 下午10:00:06
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.ui;

import java.util.List;

import weibo4android.Status;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.adapter.WeiboAdapter;
import com.droidstouch.iweibo.bean.Task;
import com.droidstouch.iweibo.logic.MainService;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class HomeActivity extends Activity implements IWeiboActivity
{


	private static final String TAG="HomeActivity";
	
	
	//加载View
	private View progresView;
	
	private View titleView;
	
	
	private ListView weiboListView;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home);
	  init();
	
		
	}


	/**
	 * 添加获取微博信息任务
	 */
	private void newTask()
	{
		Task task = new Task(Task.WEIBO_FRIEDNS_TIMELINE,null);
		
		MainService.newTask(task);
		
		MainService.addActivity(this);
	}


	public void init()
	{
		
		weiboListView = (ListView) this.findViewById(R.id.lv_weibos);
		progresView = this.findViewById(R.id.layout_progress);
		
		titleView = this.findViewById(R.id.layout_title_bar);
	  ((TextView)titleView.findViewById(R.id.txt_wb_title)).setText(MainService.nowUser.getUserName());
		
		newTask();
		
	}


	@SuppressWarnings("unchecked")
	public void refresh(Object... params)
	{
		progresView.setVisibility(View.GONE);
		
		List<Status> status = (List<Status>) params[0];
		
		
		WeiboAdapter adapter = new WeiboAdapter(this, status);
		weiboListView.setAdapter(adapter);
		
		Log.i(TAG, "ok--------");
	}
	



	
	
}
