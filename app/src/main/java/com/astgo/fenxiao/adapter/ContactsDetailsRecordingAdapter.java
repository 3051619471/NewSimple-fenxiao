package com.astgo.fenxiao.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astgo.fenxiao.R;
import com.astgo.fenxiao.tools.FileUtil;
import com.astgo.fenxiao.tools.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 * 联系人详情录音记录适配器
 */
public class ContactsDetailsRecordingAdapter extends RecyclerView.Adapter{
    private static final String TAG = "联系人详情录音记录适配器";

    private Context context;
    private List<File> files;//号码
    private int onClickTag;//当前点击item

    public ContactsDetailsRecordingAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contacts_details_callrecorder_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ContactsDetailsInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactsDetailsInfoViewHolder viewHolder = (ContactsDetailsInfoViewHolder) holder;
        viewHolder.position = position;
        viewHolder.re_iv.setImageResource(R.drawable.contacts_details_recording_start);
        viewHolder.name_tv.setText(files.get(position).getName());
        int tempDura = FileUtil.getFileDuration(files.get(position));
        viewHolder.duration_tv.setText(TimeUtil.formatLongToTimeStr((long) tempDura));
        viewHolder.progressbar.setMax(tempDura);
    }

    @Override
    public int getItemCount() {
        if(files == null){
            return 0;
        }else{
            return files.size();
        }
    }

    /**
     * 更新列表
     * @param tempList 新的数据列表
     */
    public void updateChanged(List<File> tempList){
        files = tempList;
        notifyDataSetChanged();
    }

    class ContactsDetailsInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View rootView;
        public ImageView re_iv;
        public TextView name_tv, duration_tv;
        public ProgressBar progressbar;
        public int position;

        public ContactsDetailsInfoViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.contacts_re_item);
            re_iv = (ImageView) itemView.findViewById(R.id.contacts_re_iv);
            name_tv = (TextView) itemView.findViewById(R.id.contacts_re_name);
            duration_tv = (TextView) itemView.findViewById(R.id.contacts_re_duration);
            progressbar = (ProgressBar) itemView.findViewById(R.id.contacts_re_progressbar);
            re_iv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.contacts_re_iv:
                    if(mp == null){
                        mp = new MediaPlayer();
                    }
                    // 为解决第二次播放时抛出的IllegalStateException，这里做了try-catch处理
                    try {
                        mp.isPlaying();
                    }
                    catch (IllegalStateException e) {
                        mp = null;
                        mp = new MediaPlayer();
                    }
                    if(!mp.isPlaying()){//如果录音没有播放
                        re_iv.setImageResource(R.drawable.contacts_details_recording_stop);
                        try {
                            mp.setDataSource(files.get(position).getAbsolutePath());
                            FileUtil.playWav(mp, timeHandler, timeRunnable);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        re_iv.setImageResource(R.drawable.contacts_details_recording_start);
                        count = 0;
                        progressbar.setProgress(0);
                        duration_tv.setText(TimeUtil.formatLongToTimeStr((long) progressbar.getMax()));
                        FileUtil.stopWav(mp, timeHandler, timeRunnable);
                    }
                    break;
            }
        }
        //用于控制录音播放进度条与时间
        private MediaPlayer mp;//用于播放录音
        private int count = 0;
        private Handler timeHandler = new Handler();
        private Runnable timeRunnable = new Runnable() {
            @Override
            public void run() {
                if(count < progressbar.getMax()){
                    count = count + 1000;
                    progressbar.setProgress(count);
                    duration_tv.setText(TimeUtil.formatLongToTimeStr((long) count));
                    timeHandler.postDelayed(timeRunnable, 1000);
                }else{
                    progressbar.setProgress(0);
                    count = 0;
                    re_iv.setImageResource(R.drawable.contacts_details_recording_start);
                }
            }
        };
    }
}
