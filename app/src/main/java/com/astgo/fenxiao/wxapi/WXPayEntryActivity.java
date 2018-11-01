package com.astgo.fenxiao.wxapi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.astgo.fenxiao.video.CommonActivity;
import com.blankj.utilcode.utils.ToastUtils;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.tools.PreferenceUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.newwebview.TaoWebviewActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, AppConstant.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onResp(BaseResp resp) {
		Log.i("astgo","wxonPayFinish, errCode = " + resp.errCode);

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage("微信支付结果：" + String.valueOf(resp.errCode));
//			builder.show();
//		}

		int code = resp.errCode;

		if (code == 0){
			//显示充值成功的页面和需要的操作
			ToastUtils.showLongToast("微信支付成功");
			Intent intent = new Intent(this, CommonActivity.class);
			String viewUrl = PreferenceUtil.getString(AppConstant.KEY_VIEW_URL, "");
			//String shopUrlSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX, "");
			if(!TextUtils.isEmpty(viewUrl) ){
				intent.putExtra("url",viewUrl);
			}
			startActivity(intent);
			this.finish();
		}

		if (code == -1){
			//错误
			ToastUtils.showLongToast("微信支付错误");
			finish();
		}

		if (code == -2){
			ToastUtils.showLongToast("微信支付取消");
			//用户取消
			finish();
		}
	}
}