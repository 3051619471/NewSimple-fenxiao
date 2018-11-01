package com.astgo.fenxiao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.astgo.fenxiao.R;

/**
 * Created by Administrator on 2016/4/9.
 *水纹扩散动画控件
 */
public class RingWaterView extends View {
    //控件宽高
    private int width;
    private int height;
    private int radius = width/3;//圆的半径
    private int i = 0;//控制半径的动画变量
    private int alpha = 255;//透明度
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(i < 100){
                i++;
                alpha = alpha - 2;
            }else{
                i = 0;
                alpha = 255;
            }
            invalidate();
            mHandler.postDelayed(mRunnable, 10);
        }
    };

    public RingWaterView(Context context) {
        super(context);
    }

    public RingWaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RingWaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //默认执行，计算view的宽高,在onDraw()之前
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.cyclo_bg_transparency_call_head));
//        p.setColor(Color.RED);// 设置红色
        p.setStyle(Paint.Style.STROKE);//设置画笔类型，画出图形是空心的
        p.setStrokeWidth(20);
        p.setAlpha(alpha);
        canvas.drawCircle(width / 2, height / 2, radius + i, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

    }

    /**
     * 启动动画效果
     */
    public void start(){
        mHandler.postDelayed(mRunnable, 10);
    }

    /**
     * 停止动画效果
     */
    public void stop(){
        mHandler.removeCallbacks(mRunnable);
        i = 0;
        alpha = 255;
    }

    /**
     * 设置圆形半径
     */
    public void setRadius(int radius){
        this.radius = radius;
    }


    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        Log.i("这个控件的宽度----------", "specMode=" + specMode + " specSize=" + specSize);

        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }
        Log.i("这个控件的高度----------", "specMode:" + specMode + " specSize:" + specSize);

        return specSize;
    }
}
