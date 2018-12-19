package com.astgo.naoxuanfeng.classdomel.class_activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.MainActivity;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_bean.Story_Bean;
import com.astgo.naoxuanfeng.classdomel.class_fragment.Shop_CY_Fragment;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;




public class Details_Shop_Activity extends AppCompatActivity {

    private String id1;
    private String path_details1;
    private List<Story_Bean.DataBean> data1;
    private TabLayout tabLayout;
    private ViewPager viewPage;
    private List<Fragment> frags;
    private List<String> titles =new ArrayList<>();
    private Fragment[] f;
    private Myadapter adapter;
    private String id;
    private String name;
    private TextView tv_app_title;
    private ImageView iv_app_bar_back;
    private String uid;
    private List<Story_Bean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_shop);
        ConstantURL constantURL = new ConstantURL();
        String IP_url = constantURL.IP_url;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id1 = bundle.getString("id");
        name = bundle.getString("name");
        if (id1 != null) {
            path_details1 = IP_url + "index.php?s=/Home/study/studySonCategory&id="+id1;
        } else {
            Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
        }
        initView();
        initshop();
        //创建fragment集合
        frags=new ArrayList<>();
        frags.add(new Shop_CY_Fragment());
        //动态请求的数据集合
        titles=new ArrayList<>();

        tabLayout.setupWithViewPager(viewPage);
        adapter = new Myadapter(getSupportFragmentManager());
        viewPage.setOffscreenPageLimit( titles.size() );
        //联动
        viewPage.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initView() {
        //获取资源id
        viewPage= (ViewPager) findViewById(R.id.viewPage);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        //标题
        tv_app_title = findViewById(R.id.tv_app_title);
        //返回
        iv_app_bar_back = findViewById(R.id.iv_app_bar_back);
        tv_app_title.setText(name);
        iv_app_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Details_Shop_Activity.this, MainActivity.class);
                intent.putExtra("userloginflag", 1);
                startActivity(intent);
               finish();


            }
        });


    }



    private void initshop() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_details1, new OkhttpUtils.fun1() {

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Story_Bean story_bean = gson.fromJson(string, Story_Bean.class);
                data = story_bean.getData();
                //for循环便利数据展示
                for (int i = 0; i < data.size() ; i++) {
                    String  name = data.get(i).getName();
                    id = data.get(i).getId();
                    //Toast.makeText(Details_Shop_Activity.this, id+"", Toast.LENGTH_SHORT).show();

                    //添加数据
                    titles.add(name);
                }
                //刷新适配器
                adapter.notifyDataSetChanged();
            }

        });
    }
//适配器
class Myadapter extends FragmentPagerAdapter {
    public Myadapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return getfragment(position);
    }
    @Override
    public int getCount() {
        return titles.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
    //动态创建Fragment的方法
    public Fragment  getfragment(int position){

        f=new Fragment[100];
        Fragment fg = f[position];
        if (fg == null) {
            String ids = data.get(position).getId();
            fg = Shop_CY_Fragment.getiniturl(ids+"");
            f[position] = fg;
        }
        //JCVideoPlayer.releaseAllVideos();
        return fg;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment oldFragment = null;
        if ((oldFragment = fragmentManager
                .findFragmentByTag("Shop_CY_Fragment")) != null) {
            Fragment newFragment = new Shop_CY_Fragment();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove(oldFragment);
            if (newFragment != null) {
                trans.add(R.id.fragments_container, newFragment, "Shop_CY_Fragment");
            }
            trans.commit();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
       // JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
      //  JCVideoPlayer.releaseAllVideos();
    }

}