/*
package com.astgo.naoxuanfeng.studyvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.bean.VideoModel;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by shuyu on 2016/11/11.
 *//*


public class ListVideoAdapter extends BaseAdapter {

    public final static String TAG = "TT2";
    private String path_url = "?vframe/jpg/offset/5";
    ConstantURL constantURL = new ConstantURL();
    String video_url = constantURL.Video_URL;
    private List<VideoModel> list = new ArrayList<>();
    private LayoutInflater inflater = null;
    private Context context;

    private ViewGroup rootView;

  */
/**//*


    private boolean isFullVideo;

    private GSYVideoHelper smallVideoHelper;

    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;

    public ListVideoAdapter(Context context, GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        super();
        this.context = context;
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;

        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 1; i++) {
            list.add(new VideoModel());
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_video_item, null);
            holder.videoContainer = (FrameLayout) convertView.findViewById(R.id.list_item_container);
            holder.playerBtn = (ImageView) convertView.findViewById(R.id.list_item_btn);
            holder.imageView = new ImageView(context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(video_url+"study/1.小蝌蚪找妈妈-.mp4"+path_url)
                .into(holder.imageView);
     */
/*   holder.imageView.setImageResource(R.drawable.xxx1);*//*

        smallVideoHelper.addVideoPlayer(position, holder.imageView, TAG, holder.videoContainer, holder.playerBtn);
        holder.playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                smallVideoHelper.setPlayPositionAndTag(position, TAG);
                //final String url = "https://res.exexm.com/cw_145225549855002";
                //http://phthwiy7h.bkt.clouddn.com/study/1.小蝌蚪找妈妈-.mp4
                final String url = "http://pi7q8pc1o.bkt.clouddn.com/40.蚂蚁搬虫虫-.mp4";
                gsySmallVideoHelperBuilder.setVideoTitle("title " + position)
                        .setUrl(url);
                smallVideoHelper.startPlay();

                //必须在startPlay之后设置才能生效
                //smallVideoHelper.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }


    class ViewHolder {
        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }
}
*/
