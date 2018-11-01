package com.astgo.fenxiao.tools.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.TextView;

import com.astgo.fenxiao.R;

/**
 * Created by ast009 on 2017/10/26.
 */

public class DialogUtil {

    private static DialogUtil mDialogUtil;
    private DialogUtil() {
    }

    public static DialogUtil getInstance(){
        if (mDialogUtil == null) {
            synchronized (DialogUtil.class) {
                if (mDialogUtil == null) {
                    mDialogUtil = new DialogUtil();
                }
            }
        }
        return mDialogUtil;
    }

    public Dialog showDialog(Activity activity, String txtMsg){

        final Dialog progressDialog = new Dialog(activity, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.layout_progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(txtMsg);
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                {
                    if(progressDialog.isShowing()) progressDialog.dismiss();
                }
                return false;
            }
        });

        return progressDialog;
    }


}
