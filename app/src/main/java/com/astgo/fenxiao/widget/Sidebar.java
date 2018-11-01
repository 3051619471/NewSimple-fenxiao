package com.astgo.fenxiao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.astgo.fenxiao.R;


/**
 * Created by Administrator on 2015/2/27.
 * 通讯录右侧悬浮索引字母
 */
public class Sidebar extends View {

    // 26个字母索引
    public static String[] index = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};

    public static final int SMALL = 18;
    public static final int LARGE = 32;

    // 侧边栏索引触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    private int choose = -1;    // 表示选中
    private Paint paint = new Paint();

    private TextView mTextView;

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    public Sidebar(Context context) {
        super(context);
    }

    public Sidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sidebar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色
        int height = getHeight();   //获取对应高度
        int width = getWidth();     //获取对应宽度
        int subHeight = height / index.length;  //获取每个字母的高度

        for (int i = 0; i < index.length; i++) {
            paint.setColor(getResources().getColor(R.color.theme_gray_transparency));    //画笔颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);   //粗体
            paint.setAntiAlias(true);   //抗锯齿
            if (height > 600) {
                paint.setTextSize(LARGE);
            } else {
                paint.setTextSize(SMALL);
            }

            //选中状态
            if (i == choose) {
                paint.setColor(getResources().getColor(R.color.theme_color));
                paint.setFakeBoldText(true);
            }

            // X 坐标等于中间 - 字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(index[i]) / 2;
            float yPos = subHeight * i + subHeight;
            canvas.drawText(index[i], xPos, yPos, paint);
            paint.reset();  // 重置画笔
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {

        final int action = event.getAction();
        final float y = event.getY();// 点击Y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * index.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    setBackground(new ColorDrawable(0x00000000));
                } else {
                    setBackgroundDrawable(new ColorDrawable(0x00000000));
                }
                choose = -1;
                invalidate();
                if (mTextView != null) {
                    mTextView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                // 设置右侧字母列表[A,B,C,D,E....]的背景颜色
                if (oldChoose != c) {
                    if (c >= 0 && c < index.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(index[c]);
                        }
                        if (mTextView != null) {
                            mTextView.setText(index[c]);
                            mTextView.setVisibility(View.VISIBLE);
                            mTextView.setTextColor(getResources().getColor(R.color.theme_white));
                            mTextView.setBackgroundResource(R.drawable.sidebar_choose);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
