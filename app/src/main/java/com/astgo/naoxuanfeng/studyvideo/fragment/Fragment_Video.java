/*
package com.astgo.naoxuanfeng.studyvideo.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.astgo.naoxuanfeng.R;



*/
/**
 * Created by Administrator on 2018/11/2.
 *//*


public class Fragment_Video extends Fragment {

    private ListView videoList;
    private RelativeLayout activityListVideo;
    ListVideoAdapter listVideoAdapter;
    GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    int lastVisibleItem;
    int firstVisibleItem;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       */
/* // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            .setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }*//*

        view = inflater.inflate(R.layout.ment_video, container, false);
        initView();
        //创建小窗口帮助类
        smallVideoHelper = new GSYVideoHelper(getActivity());

        //如果不设置即使用默认的 windowViewContainer
        //smallVideoHelper.setFullViewContainer(videoFullContainer);

        //配置
        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
        gsySmallVideoHelperBuilder
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setShowFullAnimation(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setVideoAllCallBack(new GSYSampleCallBack(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        Debuger.printfLog("Duration " + smallVideoHelper.getGsyVideoPlayer().getDuration() + " CurrentPosition " + smallVideoHelper.getGsyVideoPlayer().getCurrentPositionWhenPlaying());
                    }

                    @Override
                    public void onQuitSmallWidget(String url, Object... objects) {
                        super.onQuitSmallWidget(url, objects);
                        //大于0说明有播放,//对应的播放列表TAG
                        if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(ListVideoAdapter.TAG)) {
                            //当前播放的位置
                            int position = smallVideoHelper.getPlayPosition();
                            //不可视的是时候
                            if ((position < firstVisibleItem || position > lastVisibleItem)) {
                                //释放掉视频
                                smallVideoHelper.releaseVideoPlayer();
                                listVideoAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);

        listVideoAdapter = new ListVideoAdapter(getActivity(), smallVideoHelper, gsySmallVideoHelperBuilder);
        listVideoAdapter.setRootView(activityListVideo);
        videoList.setAdapter(listVideoAdapter);

        videoList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              // getActivity().firstVisibleItem = firstVisibleItem;
                lastVisibleItem = firstVisibleItem + visibleItemCount;
                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(ListVideoAdapter.TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!smallVideoHelper.isSmall()) {
                            //小窗口
                            int size = CommonUtil.dip2px(getActivity(), 150);
                            smallVideoHelper.showSmallVideo(new Point(size, size), false, true);
                        }
                    } else {
                        if (smallVideoHelper.isSmall()) {
                            smallVideoHelper.smallVideoToNormal();
                        }
                    }
                }
            }

        });

        onBackPressed();
        return view;
    }

    private void initView() {
        videoList = view.findViewById(R.id.video_list);
        activityListVideo = view.findViewById(R.id.activity_list_video);
    }



    @Override
    public void onPause() {
        super.onPause();
      //  videoPlayer.onVideoPause();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.releaseAllVideos();
       // videoPlayer.onVideoResume();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        smallVideoHelper.releaseVideoPlayer();
        GSYVideoManager.releaseAllVideos();
    }

    private void onBackPressed() {
        if (smallVideoHelper.backFromFull()) {
            return;
        }

    }


}
*/
