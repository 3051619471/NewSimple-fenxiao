package com.astgo.naoxuanfeng.studyvideo.video_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.astgo.naoxuanfeng.R;
import com.bumptech.glide.Glide;

import cn.jzvd.JzvdStd;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/12/5.
 */
//发布
public class Release_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView mVideoEwlease;
    private JzvdStd mImageShopResource;
    String urll = "?vframe/jpg/offset/25";
    Boolean num = false;
    /**
     * 发布
     */
    private Button mReleaseBut;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release_video_ment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoEwlease = (ImageView) view.findViewById(R.id.video_ewlease);
        mImageShopResource = (JzvdStd) view.findViewById(R.id.image_shop_resource);
        mVideoEwlease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceVideo();
            }
        });
        mImageShopResource.setVisibility(View.GONE);

        mReleaseBut = (Button) view.findViewById(R.id.release_but);
        mReleaseBut.setOnClickListener(this);
    }


    private void choiceVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 66);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageShopResource.setVisibility(View.VISIBLE);
        mVideoEwlease.setVisibility(View.GONE);
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            mImageShopResource.TOOL_BAR_EXIST = false;
            mImageShopResource.setUp(VIDEOPATH
                    , "", JzvdStd.SCREEN_WINDOW_LIST);
//        jcVideoPlayerStandard.loop  = true;//是否循环播放
            Glide.with(this).load(VIDEOPATH + urll)
                    .into(mImageShopResource.thumbImageView);
            mImageShopResource.widthRatio = 4;//播放比例
            mImageShopResource.heightRatio = 3;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.release_but:
                break;
        }
    }
}
