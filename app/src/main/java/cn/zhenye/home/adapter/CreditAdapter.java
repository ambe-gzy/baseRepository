package cn.zhenye.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import cn.zhenye.base.tool.ZTimeUtils;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.credit.CreditStatusManager;
import cn.zhenye.common.credit.CreditStatusViewModel;
import cn.zhenye.home.R;

public class CreditAdapter {
    private static String TAG = CreditAdapter.class.getSimpleName();
    private TextView mOneMinuteBtn;
    private TextView mFiveMinuteBtn;
    private TextView mFifteenMinuteBtn;
    private Context mContext;
    private CreditStatusViewModel mCreditStatusViewModel;

    public CreditAdapter setContext(Context context){
        mContext = context;
        return this;
    }

    public CreditAdapter setVM(CreditStatusViewModel creditStatusViewModel){
        mCreditStatusViewModel = creditStatusViewModel;
        return this;
    }

    public CreditAdapter setBtn(TextView oneMinuteBtn,TextView fiveMinuteBtn,TextView fifteenMinuteBtn){
        mOneMinuteBtn = oneMinuteBtn;
        mFiveMinuteBtn = fiveMinuteBtn;
        mFifteenMinuteBtn = fifteenMinuteBtn;
        return this;
    }

    public CreditAdapter init(AppCompatActivity appCompatActivity){
        initVM(appCompatActivity);
        initOnClickListener();
        initCountTimer();
        return this;
    }

    private void initCountTimer(){
        if (CreditStatusManager.getInstance().getOneMinuteCacheRemain() != 0) {
            CreditStatusManager.getInstance().startOneMinuteCountTimer(mOneMinuteBtn);
        }
        if (CreditStatusManager.getInstance().getFiveMinuteCacheRemain() != 0) {
            CreditStatusManager.getInstance().startFiveMinuteCountTimer(mFiveMinuteBtn);
        }
        if (CreditStatusManager.getInstance().getFifteenMinuteCacheRemain()!= 0) {
            CreditStatusManager.getInstance().startFifteenMinuteCountTimer(mFifteenMinuteBtn);
        }

    }

    private void initVM(AppCompatActivity appCompatActivity){
        if (mCreditStatusViewModel == null) {
            return;
        }
        mCreditStatusViewModel.getOneMinuteBtnStatus().observe(appCompatActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    mOneMinuteBtn.setClickable(true);
                    mOneMinuteBtn.setBackgroundResource(R.drawable.ripple_voice_reverse);
                    mOneMinuteBtn.setText("按钮1");
                }else {
                    mOneMinuteBtn.setClickable(false);
                    mOneMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                }
            }
        });
        mCreditStatusViewModel.getFiveMinuteBtnStatus().observe(appCompatActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mFiveMinuteBtn.setClickable(true);
                    mFiveMinuteBtn.setBackgroundResource(R.drawable.ripple_voice_reverse);
                    mFiveMinuteBtn.setText("按钮2");
                } else {
                    mFiveMinuteBtn.setClickable(false);
                    mFiveMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                }

            }
        });
        mCreditStatusViewModel.getFifteenMinuteBtnStatus().observe(appCompatActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mFifteenMinuteBtn.setClickable(true);
                    mFifteenMinuteBtn.setBackgroundResource(R.drawable.ripple_voice_reverse);
                    mFiveMinuteBtn.setText("按钮3");
                } else {
                    mFifteenMinuteBtn.setClickable(false);
                    mFifteenMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                }
            }
        });

        mCreditStatusViewModel.getOneMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mOneMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });
        mCreditStatusViewModel.getFiveMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mFiveMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });
        mCreditStatusViewModel.getFifteenMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mFifteenMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });

    }

    private void initOnClickListener(){
        mOneMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreditStatusManager.getInstance().getOneMinuteCredit()){
                    CreditStatusManager.getInstance().startOneMinuteCountTimer(mOneMinuteBtn);
                    //todo 获取积分
                } else {
                    ZToastUtils.showShort("请稍后");
                }
            }
        });
        mFiveMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreditStatusManager.getInstance().getFiveMinuteCredit()) {
                    CreditStatusManager.getInstance().startFiveMinuteCountTimer(mFiveMinuteBtn);
                }
            }
        });
        mFifteenMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreditStatusManager.getInstance().getFifteenMinuteCredit()) {
                    CreditStatusManager.getInstance().startFifteenMinuteCountTimer(mFifteenMinuteBtn);
                }
            }
        });
    }

}
