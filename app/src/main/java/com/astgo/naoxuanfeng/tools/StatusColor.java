package com.astgo.naoxuanfeng.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class StatusColor {
	
	public static void setStatusColor(Activity activity, int res){
		
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					setTranslucentStatus(true, activity);
				}
				//setTranslucentStatus(true, activity);
				SystemBarTintManager tintManager = new SystemBarTintManager(activity);
				tintManager.setStatusBarTintEnabled(true);
				//tintManager.setStatusBarAlpha(0.0f);
				tintManager.setStatusBarTintResource(res);//通知栏所需颜色
	        }
	

			@TargetApi(19)
			 private static void setTranslucentStatus(boolean b, Activity activity) {
				         Window win = activity.getWindow();
					     WindowManager.LayoutParams winParams = win.getAttributes();
					    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
					     if (b) {
					         winParams.flags |= bits;
					     } else {
					         winParams.flags &= ~bits;
					     }
					     win.setAttributes(winParams);
			}
}
		
		
