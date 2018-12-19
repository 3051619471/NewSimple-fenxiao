package com.astgo.naoxuanfeng.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_fragment.Shop_CY_Fragment;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_tab_Bean;
import com.astgo.naoxuanfeng.studyvideo.fragment.Study_Video_Fragment;
import com.astgo.naoxuanfeng.studyvideo.fragment.Tab_XVideo_Fragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ast009 on 2018/2/1.
 */

public class VipVideoFragment extends Fragment {
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPage;
    private List<Fragment> frags;
    private List<String> titles =new ArrayList<>();
    private Fragment[] f;
    private String id;
    private List<Video_tab_Bean.DataBean.CategoryBean> category;
   private Myadapter adapter;
private String path;
    ConstantURL constantURL = new ConstantURL();
    String ip_url = constantURL.IP_url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vipvideo, container, false);
        path=ip_url+"index.php?s=/Home/video/videoCategory";
        initView(view);
        initshop();
        //创建fragment集合
        frags=new ArrayList<>();
        frags.add(new Shop_CY_Fragment());
        //动态请求的数据集合
        titles=new ArrayList<>();

        mTabLayout.setupWithViewPager(mViewPage);
         adapter = new Myadapter(getChildFragmentManager());
        mViewPage.setOffscreenPageLimit( titles.size() );
        //联动
       mViewPage.setAdapter(adapter);
       adapter.notifyDataSetChanged();
        return view;
    }
    private void initshop() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Video_tab_Bean video_tab_bean = gson.fromJson(string, Video_tab_Bean.class);
                category = video_tab_bean.getData().getCategory();

                //for循环便利数据展示
                for (int i = 0; i < category.size() ; i++) {
                    String  name = category.get(i).getName();
                    id = category.get(i).getId();

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
       // if (fg == null) {
            if (position==0){
                String idvideo = category.get(position).getId();
                fg = Study_Video_Fragment.getiniturl(idvideo+"");
                f[position] = fg;
            }else if (position==1){
                String idvideo = category.get(position).getId();
                fg = Study_Video_Fragment.getiniturl(idvideo+"");
                f[position] = fg;
            }else if (position==2){
                String idvideo = category.get(position).getId();
                fg = Study_Video_Fragment.getiniturl(idvideo+"");
                f[position] = fg;
            }else if (position==3){
           Tab_XVideo_Fragment tab_xVideo_fragment = new Tab_XVideo_Fragment();
                return tab_xVideo_fragment;
            }else {
                 return null;
            }



        return fg;
    }
    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPage = (ViewPager) view.findViewById(R.id.viewPage);
    }
/*

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
*/

    /*@Override
    public void onResume() {
        super.onResume();
        JCVideoPlayer.releaseAllVideos();
    }*/
    }
      /*private void initView(View view) {
        tabLayout = view.findViewById(R.id.myTab);
        viewPager = view.findViewById(R.id.vp);
        initMinum();
        MyAdapterpager myAdapterpager = new MyAdapterpager(getChildFragmentManager());
        viewPager.setAdapter(myAdapterpager);
        viewPager.setOffscreenPageLimit(list.size());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                GSYVideoManager.releaseAllVideos();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }*/
   /* private void initMinum() {

        list = new ArrayList<>();
        list.add("记忆");
        list.add("演讲");
        list.add("直播");

    }*/
   /* class MyAdapterpager extends FragmentPagerAdapter {
        private Fragment_Video fragment_video;
        public MyAdapterpager(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            fragment_video = new Fragment_Video();
            return fragment_video;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }*/
    /*@Override
    public void onPause() {
        super.onPause();
       // JCVideoPlayer.releaseAllVideos();
        GSYVideoManager.releaseAllVideos();
    }*/

//    private void getData() {
//        String url = HttpUtils.API_APP+"split/tshapi/getdata";
//        OkHttpUtils.get(url)     // 请求方式和请求url
//                .tag(this)// 请求的 tag, 主要用于取消对应的请求
//                .headers("token", SPUtils.getString(AppConstant.KEY_TOKEN,""))
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        DebugUtil.error("getData结果"+s);
//                        try {
//                            JSONObject job = new JSONObject(s);
//                            if (job.getInt("code") == 1){
//                                newDataVideo = new Gson().fromJson(s,NewDataVideo.class);
//                                if (newDataVideo == null){
//                                    Log.e("SLH","newDataVideo=null");
//                                    return;
//                                }
//                                setBannerData(newDataVideo.getData().getCarouselimg());
//                                URLConstant.PLAY_IMG = newDataVideo.getData().getAdvert().get(0).getImg_url();
//                                tv_marquee.setEnabled(true);
//                                tv_marquee.setText(newDataVideo.getData().getNotice().get(0).getContent());
//                                Glide.with(getActivity()).load(newDataVideo.getData().getEncyclopedia().get(0).getImg_url()).placeholder(R.drawable.ad01).into(tv_ad_left);
//                                Glide.with(getActivity()).load(newDataVideo.getData().getEncyclopedia().get(1).getImg_url()).placeholder(R.drawable.ad02).into(tv_ad_right);
//                                tv_ad_left.setEnabled(true);
//                                tv_ad_right.setEnabled(true);
//                            }else {
//                                ToastUtils.showShortToast(job.getString("msg"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        ToastUtils.showShortToast(e.getMessage());
//                    }
//                });
//    }

   /* private void GetThumbnailPic() {
        OkHttpUtils.get(NetUrl.VIDEO_PLAY_IMG)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetThumbnailPic结果"+s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                ThumbnailBean thumbnailBean = (ThumbnailBean) BaseDataObject.getBaseBean(s,ThumbnailBean.class);
                                URLConstant.PLAY_IMG = thumbnailBean.getData().getImg_url();
                            }
                        }else {
                           // ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }*/

    /*private void GetBottomPic() {
        OkHttpUtils.get(NetUrl.VIDEO_BOTTOM_AD)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetBottomPic结果"+s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                imageBottomAD = (ImageBottomAD) BaseDataObject.getBaseBean(s,ImageBottomAD.class);
                                Glide.with(getActivity()).load(imageBottomAD.getData().get(0).getImg_url()).placeholder(R.drawable.ad01).into(tv_ad_left);
                                Glide.with(getActivity()).load(imageBottomAD.getData().get(1).getImg_url()).placeholder(R.drawable.ad02).into(tv_ad_right);
                                tv_ad_left.setEnabled(true);
                                tv_ad_right.setEnabled(true);
                            }
                        }else {
                            //ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }
*/
   /* private void GetNotificationInfo() {

        OkHttpUtils.get(NetUrl.VIDEO_NOTICE)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetNotificationInfo结果"+s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":[{"id":"1","content":"\u89c6\u9891\u516c\u544a1","url":"http:\/\/www.baidu.com"}]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                noticeBean = (NoticeBean) BaseDataObject.getBaseBean(s,NoticeBean.class);
                                tv_marquee.setText(noticeBean.getData().get(0).getContent());
                                tv_marquee.setEnabled(true);
                            }
                        }else {
                            //ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });

    }*/

   /* private void getBannerInfor() {

        OkHttpUtils.get(NetUrl.BANNER_VIDEO)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token",SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("getBannerInfor结果"+s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":[{"id":"11","img_url":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5acc2026c8b8a.png","url":"www.baidu.com"},{"id":"12","img_url":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5acc234035d76.png","url":"www.baidu.com"}]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                BannerVidoListBean bannerVidoListBean = (BannerVidoListBean) BaseDataObject.getBaseBean(s,BannerVidoListBean.class);
                                setBannerData(bannerVidoListBean.getData());
                            }
                        }else {
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }*/


      /*  banner = (Banner) view.findViewById(R.id.vipvideo_banner);

        iv_tencent = (ImageView) view.findViewById(R.id.iv_tencent);
        iv_iqiyi = (ImageView) view.findViewById(R.id.iv_iqiyi);
        iv_youku = (ImageView) view.findViewById(R.id.iv_youku);
        iv_pptv = (ImageView) view.findViewById(R.id.iv_pptv);
        iv_mgtv = (ImageView) view.findViewById(R.id.iv_mgtv);
        iv_haokan = (ImageView) view.findViewById(R.id.iv_haokan);
        iv_letv = (ImageView) view.findViewById(R.id.iv_letv);
        //iv_1905 = (ImageView) view.findViewById(R.id.iv_1905);
        iv_meipai = (ImageView) view.findViewById(R.id.iv_meipai);

        tv_ad_left = (ImageView) view.findViewById(R.id.tv_ad_left);
        tv_ad_left.setEnabled(false);
        tv_ad_right = (ImageView) view.findViewById(R.id.tv_ad_right);

        tv_ad_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBottomAD==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",imageBottomAD.getData().get(0).getUrl());
                startActivity(intent);
            }
        });
        tv_ad_right.setEnabled(false);
        tv_ad_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBottomAD==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",imageBottomAD.getData().get(1).getUrl());
                startActivity(intent);
            }
        });

        iv_tencent.setOnClickListener(this);
        iv_iqiyi.setOnClickListener(this);
        iv_youku.setOnClickListener(this);
        iv_pptv.setOnClickListener(this);

        iv_haokan.setOnClickListener(this);
        iv_letv.setOnClickListener(this);
        //iv_1905.setOnClickListener(this);
        iv_meipai.setOnClickListener(this);
        iv_mgtv.setOnClickListener(this);
        tv_marquee = (MarqueeTextView) view.findViewById(R.id.tv_auto_scroll);
        tv_marquee.setEnabled(false);
        tv_marquee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeBean==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",noticeBean.getData().get(0).getUrl());
                startActivity(intent);
            }
        });
        //tv_marquee.setText("好消息！");
*/


// private List<BannerInfo> bannerInfors = new ArrayList<>();

   /* private void setBannerData(final List<BannerVidoListBean.DataBean> bannerInfors) {

        List<String> bannerImaglist = new ArrayList<>();
            for (BannerVidoListBean.DataBean bannerInfo : bannerInfors) {
                bannerImaglist.add(bannerInfo.getImg_url());
            }
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide01);
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide02);
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide03);
        banner.setImages(bannerImaglist).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("SLH",""+position);
                BannerVidoListBean.DataBean bannerInfo = bannerInfors.get(position);
                if (bannerInfo != null) {
                    Intent intent = new Intent(getActivity(), CommonActivity.class);
                    intent.putExtra("url",bannerInfo.getUrl());
                    startActivity(intent);
                }else {
                    Log.e("SLH","bannerInfo == null"+position);
                }
            }
        });
//        banner.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void OnBannerClick(int position) {
//
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        if (jumpVipVideo(v)) return;
    }

    private boolean jumpVipVideo(View v) {
        // Intent intent = new Intent(this.getActivity(), VideoListActivity.class);
        Intent intent = new Intent(this.getActivity(), WebVideoListActivity.class);
        //1：腾讯视频-手机版链接、2：爱奇艺、3：优酷、4：芒果TV、5：乐视、6：PPTV、7：搜狐视频、8：看看视频、9：暴风影音、10：1905
        if (v.getId() == R.id.iv_tencent) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_TENCENT);
            intent.putExtra("platform",1);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_iqiyi) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_IQIYI);
            intent.putExtra("platform",2);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_youku) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_YOUKU);
            intent.putExtra("platform",3);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_pptv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_PPTV);
            intent.putExtra("platform",6);
            startActivity(intent);
            return true;

        }
        if (v.getId() == R.id.iv_haokan) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_HAO_KAN);
            intent.putExtra("platform",999);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_letv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_LE_TV);
            intent.putExtra("platform",5);
            startActivity(intent);
            return true;
        }
//        if (v.getId() == R.id.iv_1905) {
//            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_1905_TV);
//            intent.putExtra("platform",10);
//            startActivity(intent);
//            return true;
//        }
        if (v.getId() == R.id.iv_meipai) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_MEIPAI_TV);
            intent.putExtra("platform",999);
            startActivity(intent);
            return true;

        }
        if (v.getId() == R.id.iv_mgtv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_MG_TV);
            intent.putExtra("platform",4);
            startActivity(intent);
            return true;

        }
        return false;
    }*/


