package com.droidstouch.iweibo.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.adapter.UserInfoAdapter;
import com.droidstouch.iweibo.bean.Task;
import com.droidstouch.iweibo.bean.UserInfo;
import com.droidstouch.iweibo.logic.MainService;
import com.droidstouch.iweibo.services.UserInfoServices;
import com.droidstouch.iweibo.util.SharedPreferencesUtil;




/**
 * 用户登录Activity
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class LoginActivity extends Activity implements IWeiboActivity
{

	
	
	
	private UserInfoServices services;
	private List<UserInfo> users;
	private UserInfo nowUser;
	
	
	
	private Button btn_user_selecte;
	private ImageView imge_user_head;
	private TextView txt_login_show_name;
	private ProgressDialog progressDialog;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		//启动主服务
		Intent service=new Intent(this, MainService.class);
		startService(service);
		
		services = new UserInfoServices(this);
		
		
		
		
		// 把自己添加到Activity集合里面
		MainService.addActivity(this);
		
		
		
		init();
		
		nowUser=SharedPreferencesUtil.getLoginUser(this);
	
		Log.i("LoginActivity",nowUser.toString());
		
		if(null != nowUser)
		{
			txt_login_show_name.setText(nowUser.getUserName());
			nowUser = services.getUserInfoByUserId(nowUser.getUserId());
			imge_user_head.setImageDrawable(nowUser.getUserIcon());
			
			showDialg();
			newTask();
			
		
		}
		
		
	}

	
	void initView()
	{
		imge_user_head = (ImageView) this.findViewById(R.id.imge_user_head);
		txt_login_show_name = (TextView) this.findViewById(R.id.txt_login_show_name);

		btn_user_selecte = (Button) this.findViewById(R.id.btn_user_selected);
		btn_user_selecte.setOnClickListener(new UserSelectedOnClickListener());
		
		Button btn_add_account = (Button) this.findViewById(R.id.btn_add_account);
		Button btn_login = (Button) this.findViewById(R.id.btn_login);
		
		btn_add_account.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		
		btn_login.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				///打开进度框
				showDialg();
				
				// 保存登录用户信息
				SharedPreferencesUtil.saveLoginUser(LoginActivity.this,nowUser);
				//发送登录任务给系统
				newTask();
			}
		});
	}
	
	
	
	
	
	public void init()
	{
		
		
		users = services.findAllUsers();
		
		if(null==users || users.isEmpty())
		{
			Intent intent = new Intent(this, AuthActivity.class);
			startActivity(intent);
			finish();
		}
		initView();
	}


	public void refresh(Object... params)
	{

		progressDialog.dismiss();
		jump();
	}
	
	
	
	private void jump()
	{
	//跳转
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}
	
	
	private void newTask()
	{
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("user", nowUser);
		
		Task task = new Task(Task.WEIBO_LOGIN, taskParams);
		
		MainService.newTask(task);
	}
	
	
	private void showDialg()
	{
		
		if(null == progressDialog)
		{
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setMessage("正在登录...");
		progressDialog.show();
	}
	
	
	
	
	
	
	final class UserSelectedOnClickListener implements OnClickListener
	{

		public void onClick(View v)
		{
			
			View viewDlg = View.inflate(LoginActivity.this, R.layout.user_selected_dialog, null);
			
			final Dialog dialog = new Dialog(LoginActivity.this, R.style.user_selected_dialog);
			dialog.setContentView(viewDlg);
			
			dialog.show();
			
			
			ListView listView = (ListView) viewDlg.findViewById(R.id.lv_user_list);
			
			UserInfoAdapter adapter = new UserInfoAdapter(LoginActivity.this, users);
			
			listView.setAdapter(adapter);
			
			
			
			listView.setOnItemClickListener(new OnItemClickListener()
			{

				public void onItemClick(AdapterView<?> arg0, View view, int arg2,
						long id)
				{
					
					
					ImageView imagUserHead=(ImageView) view.findViewById(R.id.img_user_head_temp);
					TextView txtUserName=(TextView) view.findViewById(R.id.txt_show_name);
					
					
					imge_user_head.setImageDrawable(imagUserHead.getDrawable());
					txt_login_show_name.setText(txtUserName.getText());
					
					nowUser = services.getUserInfoByUserId(id+"");
					
					dialog.dismiss();
					
					
				}
			});
			
			
			
		}
		
		
	}
	
	
	
	
	
}
