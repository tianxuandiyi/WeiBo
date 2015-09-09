/**
 *WeiboAdapter.java
 *2011-9-29 下午08:18:26
 *Touch Android
 *http://bbs.droidstouch.com
 */
package com.droidstouch.iweibo.adapter;

import java.util.List;

import com.droidstouch.iweibo.R;
import com.droidstouch.iweibo.util.StringUtil;

import weibo4android.Status;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author <a href="http://bbs.droidstouch.com">Touch Android</a>
 *
 */
public class WeiboAdapter extends BaseAdapter
{

	
	private List<Status> status;
	private Context context;
	
	
	
	public WeiboAdapter(Context context,List<Status> status) 
	{
		
		this.status = status;
		this.context = context;
		
	}
	
	
	public int getCount()
	{
		return status==null?0:status.size();
	}

	public Object getItem(int position)
	{
		return  status==null?null:status.get(position);
	}

	public long getItemId(int position)
	{
		return status.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		
		View view = convertView;
		
		
		if(null == view)
		{
			
			WeiboHolder holder = new WeiboHolder();
			
			Status s = this.status.get(position);
			
			view  = LayoutInflater.from(context).inflate(R.layout.wb_item_template, null);
			
			holder.txt_wb_item_uname = (TextView) view.findViewById(R.id.txt_wb_item_uname);
			holder.txt_wb_item_content= (TextView) view.findViewById(R.id.txt_wb_item_content);
			
			holder.txt_wb_item_from= (TextView) view.findViewById(R.id.txt_wb_item_from);
			holder.txt_wb_item_from.setMovementMethod(LinkMovementMethod.getInstance());
			
			holder.txt_wb_item_uname.setText(s.getUser().getName());
			holder.txt_wb_item_content.setText(s.getText());
			
			holder.txt_wb_item_from.setText("来着:"+Html.fromHtml(s.getSource()));
			
			
			
			
			
			//判断是否通过认证
			if(s.getUser().isVerified())
			{
				holder.img_wb_item_V = (ImageView) view.findViewById(R.id.img_wb_item_V);
				holder.img_wb_item_V.setVisibility(View.VISIBLE);
			}
			
			//判断微博中是否含有图片
			if(!StringUtil.isEmpty(s.getThumbnail_pic()))
			{
				holder.img_wb_item_content_pic = (ImageView) view.findViewById(R.id.img_wb_item_content_pic);
				holder.img_wb_item_content_pic.setVisibility(View.VISIBLE);
			}
			
			// 判断使用又转发
			if(s.getRetweeted_status()!=null)
			{
				holder.lyt_wb_item_sublayout = (LinearLayout) view.findViewById(R.id.lyt_wb_item_sublayout);
				
				holder.lyt_wb_item_sublayout.setVisibility(View.VISIBLE);
				
				
				holder.txt_wb_item_subcontent = (TextView) view.findViewById(R.id.txt_wb_item_subcontent);
				holder.txt_wb_item_subcontent.setText(s.getRetweeted_status().getText());
				
				
				//判断微博中是否含有图片
				if(!StringUtil.isEmpty(s.getRetweeted_status().getThumbnail_pic()))
				{
					holder.img_wb_item_content_subpic = (ImageView) view.findViewById(R.id.img_wb_item_content_subpic);
					holder.img_wb_item_content_subpic.setVisibility(View.VISIBLE);
				}
				
			}
			
			
			
		}
		
		return view;
	}

	
	private static class WeiboHolder
	{
		
		ImageView img_wb_item_head;
		
		TextView txt_wb_item_uname;
		
		ImageView img_wb_item_V;
		
		TextView txt_wb_item_time;
		
		TextView txt_wb_item_content;
		
		ImageView img_wb_item_content_pic;
		
		LinearLayout lyt_wb_item_sublayout;
		
		TextView txt_wb_item_subcontent;
		
		ImageView img_wb_item_content_subpic;
		
		TextView txt_wb_item_from;
		
		TextView txt_wb_item_redirect;
		
		TextView txt_wb_item_comment;
		
	}
	
	
	
}
