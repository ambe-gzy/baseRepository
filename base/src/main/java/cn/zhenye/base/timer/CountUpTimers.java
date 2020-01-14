package cn.zhenye.base.timer;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhaibinme on 2019/1/31
 */
public class CountUpTimers {
    private String TAG = "<<<";
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private Handler mHandler = null;
    public static float mCurrentCount = 0;
    private boolean isPause = false;
    private static final float SECOND_TIME = 1000f;
    private static final int delay = 1000;  //1s
    private static final int period = 100;  //1s
    private static final int UPDATE_TIME = 0;

    private OnCountUpListener mOnCountUpListener;
    private boolean hasStart = false;

    @SuppressLint("HandlerLeak")
    public CountUpTimers(OnCountUpListener listener) {
        this.mOnCountUpListener = listener;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TIME:
                        updateTime();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void updateTime() {
        if (mOnCountUpListener != null) {
            mOnCountUpListener.onTick(mCurrentCount);
        }
    }

    public void pauseTimer() {
        isPause = true;
    }

    public void resumeTimer() {
        isPause = false;
    }

    public void restartTimer() {
        isPause = false;
        mCurrentCount = 0;
        startTimer();
    }

    public void startTimer() {
        if (hasStart && mTimer != null && mTimerTask != null) {
            resumeTimer();
            return;
        }

        isPause = false;

        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "mCurrentCount: " + String.valueOf(mCurrentCount));
                    sendMessage(UPDATE_TIME);
                    do {
                        try {
                            Log.i(TAG, "sleep(1000)...");
                            Thread.sleep(period);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);
                    mCurrentCount = mCurrentCount + period / SECOND_TIME;
                }
            };
        }

        if (mTimer != null && mTimerTask != null)
            mTimer.schedule(mTimerTask, delay, period);

        hasStart = true;
    }

    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public void sendMessage(int id) {
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, id);
            mHandler.sendMessage(message);
        }
    }

    public interface OnCountUpListener {
        public void onTick(float time);
    }
}
