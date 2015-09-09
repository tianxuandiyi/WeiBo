package com.droidstouch.iweibo.bean;

import java.util.Map;

public class Task
{

	//任务ID
	private int taskId;
	
	//参数
	private Map<String, Object> taskParams;
	
	
	
	//登录
	public static final int WEIBO_LOGIN=1;
	// 获取授权信息
	public static final int GET_ACCESS_TOKEN=2;
	//获取授权地址
	public static final int GET_AUTH_URL=3;
	
	// 获取用户头像
	public static final int GET_USER_HEAD=4;
	
	//获取当前登录用户及其所关注用户的最新微博消息
	public static final int WEIBO_FRIEDNS_TIMELINE=5;
	
	
	
	public Task(int taskId, Map<String, Object> taskParams)
	{
		this.taskId = taskId;
		this.taskParams = taskParams;
	}



	public int getTaskId()
	{
		return taskId;
	}



	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
	}



	public Map<String, Object> getTaskParams()
	{
		return taskParams;
	}



	public void setTaskParams(Map<String, Object> taskParams)
	{
		this.taskParams = taskParams;
	}
	
	
	
	
	
	
	
	
}
