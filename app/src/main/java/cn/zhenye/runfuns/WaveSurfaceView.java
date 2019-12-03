package cn.zhenye.runfuns;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class WaveSurfaceView extends SurfaceView implements Drawable.Callback {
    //总的数据次数
    private int mDataNum = 0;

    //是否允许绘制
    private boolean mIsAllowDraw = false;

    //一个数据的绘制次数,通过其他参数获得
    private int mDrawNum ;

    //采样点数
    private int mSamplingSize;

    //每次绘制增加的点数
    private int mAddPixelNum;

    //帧率
    private static final int FRAME = 30;

    /**
     * 帧率为30帧，即每秒绘制 {@link #FRAME} 次，每次增加的点数为{@link #mAddPixelNum}
     * ,则每秒绘制的点数为FRAME*mAddPixelNum個點，需保证mDrawNum的个数大于每秒绘制的最大点数。
     */




    public WaveSurfaceView(Context context) {
        super(context);
    }

    public WaveSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaveSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }




}
