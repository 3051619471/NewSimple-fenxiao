package com.astgo.naoxuanfeng.tools.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.tools.DensityUtil;

public class ToastUtil {
	private static Toast mToast;
	public static void toast(Context context, int textRes) {
		CharSequence text = context.getResources().getText(textRes);
		makeText(context, text).show();
	}

	public static void toast(Context context, CharSequence sequence) {
		Toast toast =  makeText(context, sequence);
		if(toast != null){
//			toast.cancel();
			toast.show();
		}
	}

	public static void showToast(Context context, String message) {
		if(mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);// 居中显示
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	public static void toast(Context context, int textRes, int gravity) {
		CharSequence text = context.getResources().getText(textRes);
		makeText(context, text,gravity).show();
	}
	
	public static void toast(Context context, CharSequence sequence, int gravity) {
		makeText(context, sequence,gravity).show();
	}

	public static void toastWithCat(Context context, int textRes, boolean isHappy) {
		CharSequence text = context.getResources().getText(textRes);
		toastWithCat(context, text, isHappy);
	}

	public static void toastWithCat(Context context, CharSequence text, boolean isHappy) {
		Toast result = new Toast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.m_ui_toast, null);
		result.setView(v);
		ImageView iv = (ImageView) v.findViewById(android.R.id.icon);

		TextView tv = (TextView) v.findViewById(android.R.id.message);
		tv.setText(text);

		result.setGravity(Gravity.BOTTOM, 0, 0);
		result.setDuration(Toast.LENGTH_SHORT);
		result.show();
	}
	
	@SuppressLint("ShowToast")
	private static Toast makeText(Context context, CharSequence text) {
		Toast result = new Toast(context);
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.m_ui_toast, null);
		result.setView(v);
		TextView tv = (TextView) v.findViewById(android.R.id.message);
		tv.setText(text);

		int yOffset = DensityUtil.dip2px(context,context.getResources().getInteger(R.integer.config_toast_y_offset));
		result.setGravity(Gravity.BOTTOM, 0, yOffset);
		result.setDuration(Toast.LENGTH_SHORT);
		
		return result;
//		return Toast.makeText(context, text, Toast.LENGTH_SHORT);
	}

	@SuppressLint("ShowToast")
	private static Toast makeText(Context context, CharSequence text, int gravity) {
		Toast result = new Toast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.m_ui_toast, null);
		result.setView(v);
		TextView tv = (TextView) v.findViewById(android.R.id.message);
		tv.setText(text);

		int yOffset =  DensityUtil.dip2px(context,context.getResources().getInteger(R.integer.config_toast_y_offset));
		result.setGravity(gravity, 0, yOffset);
		result.setDuration(Toast.LENGTH_SHORT);
		
		return result;
	}

	public static void toastInThread(final Activity context, final String msg){
		context.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}

		});
	}





}
