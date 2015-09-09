package com.droidstouch.iweibo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.droidstouch.iweibo.R;

public class LogoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
      //鍙栨秷鏍囬
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //鍙栨秷鐘舵�鏍�
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.logo);
        
        
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);
        
        ImageView img_logo = (ImageView) this.findViewById(R.id.img_logo);
        img_logo.setAnimation(animation);
        
        
        animation.setAnimationListener(new AnimationListener(){

			public void onAnimationEnd(Animation animation)
			{

				 Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
			     startActivity(intent);
			}

			public void onAnimationRepeat(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}

			public void onAnimationStart(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}});
        
       
        
    }
}