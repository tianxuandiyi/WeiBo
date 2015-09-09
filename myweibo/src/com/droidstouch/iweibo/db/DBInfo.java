package com.droidstouch.iweibo.db;

public class DBInfo
{

	
	public static class DB
	{
		
		//数据库名称
		public static  final String DB_NAME="iweibo"; 
		
		// 数据库版本
		public static final int  VERSION=1;
		
	}
	
	
	public static class Table
	{
		
		public static final String USER_INFO_TB_NAME="UserInfo";
		public static final String USER_INFO_CREATE="CREATE TABLE IF NOT EXISTS  " + USER_INFO_TB_NAME + " ( _id INTEGER PRIMARY KEY,userId TEXT, userName TEXT, token TEXT,tokenSecret TEXT,isDefault TEXT,userIcon BLOB)";
		public static final String USER_INFO_DROP="DROP TABLE " + USER_INFO_TB_NAME; 
		
		
	}
	
	
	
}
