package com.astgo.naoxuanfeng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.astgo.naoxuanfeng.MyApplication;
import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.adapter.ChooseListAdapter;
import com.astgo.naoxuanfeng.tools.PreferenceUtil;

/**
 * Created by Administrator on 2016/4/11.
 * 通话设置详情界面
 */
public class SettingDetailsActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "通话设置详情界面";
    private String tag;
    private TextView tv_settings_description;
    private LinearLayout setting01_ll, setting02_ll, setting03_ll;
    private ListView setting01_list;
    private ChooseListAdapter setting01_adapter;
    private String[] setting01_data;

    private ToggleButton setting02_AJSY, setting02_YYTX, setting03_BHTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_details);
        tag = getIntent().getStringExtra(MyConstant.SETTINGS_DETAIL_TAG);
        initView(tag);
    }

    /**
     * 初始化界面
     */
    private void initView(String tag){
        initTitleView();
        if(MyConstant.SP_SETTINGS_BDFS.equals(tag)){//拨打方式
            initSetting01View();
        }
        if(MyConstant.SP_SETTINGS_AJSY.equals(tag)){//声音设置
            initSetting02View();
        }
        if(MyConstant.SP_SETTINGS_BHTS.equals(tag)){//拨号提示
            initSetting03View();
        }
    }

    /**
     * 初始化title
     */
    private void initTitleView() {
        if(MyConstant.SP_SETTINGS_BDFS.equals(tag)){//拨打方式
            ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.setting_txt_01));
        }
        if(MyConstant.SP_SETTINGS_AJSY.equals(tag)){//声音设置
            ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.setting_txt_02));
        }
        if(MyConstant.SP_SETTINGS_BHTS.equals(tag)){//拨打提示
            ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.setting_txt_03));
        }
        ((ImageView)findViewById(R.id.title_left)).setImageResource(R.mipmap.icon_back_black);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    private void initSetting01View(){
        setting01_ll = (LinearLayout) findViewById(R.id.setting01_ll);
        setting01_ll.setVisibility(View.VISIBLE);
        setting01_list = (ListView) findViewById(R.id.setting01_BDFS);
        setting01_data = getResources().getStringArray(R.array.setting01_bdfs);
        setting01_adapter = new ChooseListAdapter(this, setting01_data, MyApplication.mSpInformation.getInt(MyConstant.SP_SETTINGS_BDFS, MyConstant.CALL_BACK_TAG));
        setting01_list.setAdapter(setting01_adapter);
        setting01_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setting01_adapter.chooseItem(i);
                PreferenceUtil.setValue(MyConstant.SP_SETTINGS_BDFS, i);
            }
        });
    }

    private void initSetting02View(){
        setting02_ll = (LinearLayout) findViewById(R.id.setting02_ll);
        setting02_ll.setVisibility(View.VISIBLE);
        setting02_AJSY = (ToggleButton) findViewById(R.id.setting02_AJSY);
        setting02_YYTX = (ToggleButton) findViewById(R.id.setting02_YYTX);
        setting02_AJSY.setChecked(MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_AJSY, true));
        setting02_YYTX.setChecked(MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_YYTX, false));
        setting02_AJSY.setOnClickListener(this);
        setting02_YYTX.setOnClickListener(this);
    }
    private void initSetting03View(){
        tv_settings_description = (TextView) findViewById(R.id.tv_settings_description);
        tv_settings_description.setText("开启拨打提示，正常拨号前会弹出提示框，供您选择拨打方式");
        tv_settings_description.setVisibility(View.VISIBLE);
        setting03_ll = (LinearLayout) findViewById(R.id.setting03_ll);
        setting03_ll.setVisibility(View.VISIBLE);
        setting03_BHTS = (ToggleButton) findViewById(R.id.setting03_BHTS);
        setting03_BHTS.setChecked(MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_BHTS, true));
        setting03_BHTS.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left://返回按钮
                finish();
                break;
            case R.id.setting02_AJSY://按键声音
                PreferenceUtil.setValue(MyConstant.SP_SETTINGS_AJSY, setting02_AJSY.isChecked());
                break;
            case R.id.setting02_YYTX://语音提醒
                PreferenceUtil.setValue(MyConstant.SP_SETTINGS_YYTX, setting02_YYTX.isChecked());
                break;
            case R.id.setting03_BHTS://拨号提示
                PreferenceUtil.setValue(MyConstant.SP_SETTINGS_BHTS, setting03_BHTS.isChecked());
                break;
        }
    }
}
