package com.droidstouch.iweibo.ui;


/***
 * Activity 接口
 * @author <a href="http://bbs.droidstouch.com">Touch Andorid</a>
 *
 */
public interface IWeiboActivity
{

	/**
	 * 初始化数据
	 */
	void init();
	
	/**
	 * 刷新UI
	 */
	void refresh(Object... params);
	
}
