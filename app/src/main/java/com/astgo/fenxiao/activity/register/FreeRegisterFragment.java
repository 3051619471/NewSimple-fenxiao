package com.astgo.fenxiao.activity.register;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.tools.MD5EncodeUtil;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.PreferenceUtil;
import com.astgo.fenxiao.tools.Tt;
import com.astgo.fenxiao.tools.XmlUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是免费注册的交互界面，只需要输入注册手机号，验证成功即可进入下一步获取验证码
 */
public class FreeRegisterFragment extends Fragment implements View.OnClickListener {

    private TextView tvError;
    private EditText etPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFBI(view);
    }

    private void initFBI(View view) {
        tvError = (TextView) view.findViewById(R.id.tv_error);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setText("");
            }
        });
    }

    // 设置免费注册参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, etPhone.getText().toString());
        urlParams.put(MyConstant.KEY, MD5EncodeUtil.getMD5EncodeStr(etPhone.getText().toString() + MyConstant.KEY_FLAG));
        urlParams.put(MyConstant.CODETYPE, MyConstant.VOC);
        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
        urlParams.put(MyConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(etPhone.getText())) {
                    Tt.showShort(getActivity(), getString(R.string.register_free_empty_params));
                } else {
                    if (NetworkUtil.isConnection(getActivity())) {
//                        WebServiceUtil.postStringData(NetUrl.GETREGCODE, getUrlParam(), new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String s) {
//                                switch (XmlUtil.getRetXML(s)) {
//                                    case 0://获取验证码成功
//                                        redirect(s);
//                                        break;
//                                    case 1://号码已被限制使用
//                                        tvError.setText(getString(R.string.register_free_api_ret_1));
//                                        break;
//                                    case 5://号码已被注册
//                                        tvError.setText(getString(R.string.register_free_api_ret_2));
//                                        break;
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//
//                            }
//                        });
                    }
                }
                break;
        }
    }

    // 跳转到验证码验证界面
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect(String result) {
        // 保存账户信息
        PreferenceUtil.setValue(MyConstant.SP_ACCOUNT, etPhone.getText().toString());
        // 跳转验证码验证界面
        Intent intent = new Intent(getActivity(), VerificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(VerificationActivity.VERIFICATION,
                XmlUtil.parserFilterXML(result, MyConstant.VERIFY_VAL));
        startActivity(intent);
    }

}
