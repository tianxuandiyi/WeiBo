package com.droidstouch.iweibo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.droidstouch.iweibo.R;

public class AuthActivity extends Activity
{

	
	
	private Dialog dialog;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.auth);
		
		
		//加载Dialog布局文件
		View digView = View.inflate(this, R.layout.authorize_dialog, null);
		
		//实例化一个Dialog对象，并且用R.style.auth_dialog作为样式
		dialog = new  Dialog(this, R.style.auth_dialog);
		dialog.setContentView(digView);//dialog使用digView作为布局文件
		dialog.show();
		
		
		Button btnBegin=(Button) digView.findViewById(R.id.btn_auth_begin);
		
		
		btnBegin.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(AuthActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		
	}
}
