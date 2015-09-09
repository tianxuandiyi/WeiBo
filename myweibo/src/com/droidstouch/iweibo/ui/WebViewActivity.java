package com.droidstouch.iweibo.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.Task;
import com.droidstouch.iweibo.logic.MainService;
import com.droidstouch.iweibo.util.JavascriptInterface;



/**
 * 
 * @author Touch Android
 * http://bbs.droidstouch
 * 新浪微博OAuth授权页面
 */
public class WebViewActivity extends Activity implements IWeiboActivity
{

	
	
	private WebView webView;
	private ProgressDialog progressDialog;
	private static final int CLOSE_DLG=1;
	private String url=null;
	private Handler handler ;
	
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.webview);
		
		
		
		init();
		
		
		Task task = new Task(Task.GET_AUTH_URL, null);
		
		MainService.newTask(task);
		MainService.addActivity(this);
		
		
		
		handler = new Handler()
		{
			
			public void handleMessage(android.os.Message msg) 
			{
				if(msg.what ==CLOSE_DLG)
				{
					progressDialog.dismiss();
				}
				
			};
		};
		
	}
	
	
	
	public void init()
	{
		
		if(progressDialog == null)
		{
			progressDialog  = new ProgressDialog(this);
		}
		progressDialog.setMessage(getString(R.string.loading_auth_url));
		progressDialog.show();
		
		
		webView = (WebView) this.findViewById(R.id.wv_oauth);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavascriptInterface(), "Methods");
		
		webView.setWebViewClient(new WebViewClient(){
			
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				//load(url,webView);
				return true;
			}
			
			
			public void onPageFinished(WebView view, String url)
			{
				if(url.equals("http://api.t.sina.com.cn/oauth/authorize"))
				{
					view.loadUrl("javascript:window.Methods.getPin('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				
					Intent intent = new Intent(WebViewActivity.this,AccessTokenActivity.class);
					startActivity(intent);
				}
				
				super.onPageFinished(view, url);
			}
			
		});
		
		
		
		webView.setWebChromeClient(new WebChromeClient(){
			public void onProgressChanged(WebView view, int newProgress)
			{
				if(newProgress == 100)
				{
					handler.sendEmptyMessage(CLOSE_DLG);
				}
				super.onProgressChanged(view, newProgress);
			}
			
		});
		
	}
	
	
	
	public void load(final String url,final WebView view)
	{
		
		if(null ==url || "".equals(url))
			return;
		
		new Thread()
		{
			
			public void run()
			{
				view.loadUrl(url);
			}
		}.start();
		
	}



	@Override
	public void refresh(Object... params)
	{
		progressDialog.setMessage(getString(R.string.loading));
		url = (String) params[0];
		Log.i("WebViewActivity", "url" +url);
		load(url,webView);
	}
	
	
	
	
}
