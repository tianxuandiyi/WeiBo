/**
 *UserInfoAdapter.java
 *2011-9-10 下午10:35:21
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.bean.UserInfo;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class UserInfoAdapter extends BaseAdapter
{

	
	
	private Context context;
	private List<UserInfo> users;
	
	public UserInfoAdapter(Context context,List<UserInfo> users)
	{
		this.context = context;
		this.users = users;
		
	}
	
	public int getCount()
	{
		return users==null?0:users.size();
	}

	public Object getItem(int position)
	{
		return users==null?null:users.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Long.parseLong(users.get(position).getUserId());
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		
		View v  = convertView;
		
		if(null ==v)
		{
			v=LayoutInflater.from(context).inflate(R.layout.users_selected_templete, null);
			
			ImageView imagUserHead=(ImageView) v.findViewById(R.id.img_user_head_temp);
			TextView txtUserName=(TextView) v.findViewById(R.id.txt_show_name);
			
			UserInfo userInfo =users.get(position);
			if(userInfo.getUserIcon() != null)
				imagUserHead.setImageDrawable(userInfo.getUserIcon());
			txtUserName.setText(userInfo.getUserName());
			
		}
		
		
		return v;
	}

}
