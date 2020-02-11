package cn.zhenye.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import cn.zhenye.base.tool.ZTimeUtils;
import cn.zhenye.common.credit.dialog.CreditResultDialog;
import cn.zhenye.common.credit.manager.CreditOperationManager;
import cn.zhenye.common.credit.manager.CreditManager;
import cn.zhenye.common.credit.manager.CreditStatusManager;
import cn.zhenye.common.credit.VM.CreditStatusViewModel;
import cn.zhenye.home.R;

public class CreditStatusAdapter {
    private static String TAG = CreditStatusAdapter.class.getSimpleName();
    private TextView mOneMinuteBtn;
    private TextView mFiveMinuteBtn;
    private TextView mFifteenMinuteBtn;
    private Context mContext;
    private CreditStatusViewModel mCreditStatusViewModel;
    private View mContainer;
    private TextView mOneMinuteBtnMessage;
    private TextView mFiveMinuteBtnMessage;
    private TextView mFifteenMinuteBtnMessage;

    public CreditStatusAdapter setContext(Context context){
        mContext = context;
        return this;
    }

    public CreditStatusAdapter setVM(CreditStatusViewModel creditStatusViewModel){
        mCreditStatusViewModel = creditStatusViewModel;
        return this;
    }

    public CreditStatusAdapter setBtn(TextView oneMinuteBtn, TextView fiveMinuteBtn, TextView fifteenMinuteBtn){
        mOneMinuteBtn = oneMinuteBtn;
        mFiveMinuteBtn = fiveMinuteBtn;
        mFifteenMinuteBtn = fifteenMinuteBtn;
        return this;
    }

    public CreditStatusAdapter init(AppCompatActivity appCompatActivity){
        initVM(appCompatActivity);
        initOnClickListener(appCompatActivity);

        initCountTimer();
        return this;
    }

    public CreditStatusAdapter setContainer(View container){
        mContainer = container;
        initView();
        return this;
    }

    public void initView(){
        if (mContainer == null){
            return;
        }
        mOneMinuteBtnMessage = mContainer.findViewById(R.id.tv_get_credit_message_one);
        mFiveMinuteBtnMessage = mContainer.findViewById(R.id.tv_get_credit_message_five);
        mFifteenMinuteBtnMessage = mContainer.findViewById(R.id.tv_get_credit_messgae_fifteen);

        mOneMinuteBtnMessage.setText(mContext.getResources().getString(R.string.credit_get,
                CreditOperationManager.MIN_CREDIT_1*10,CreditOperationManager.MIN_CREDIT_2*10));
        mFiveMinuteBtnMessage.setText(mContext.getResources().getString(R.string.credit_get,
                CreditOperationManager.MIN_CREDIT_1*10,CreditOperationManager.MIN_CREDIT_3*10));
        mFifteenMinuteBtnMessage.setText(mContext.getResources().getString(R.string.credit_get,
                CreditOperationManager.MIN_CREDIT_1*10,CreditOperationManager.MAX_CREDIT_5*10));
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
                    mOneMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                    mOneMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                    mOneMinuteBtn.setText("获取");
                }else {
                    mOneMinuteBtn.setClickable(false);
                    mOneMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.color_2E2E2E));
                    mOneMinuteBtn.setBackgroundResource(R.drawable.bg_btn_unable);
                }
            }
        });
        mCreditStatusViewModel.getFiveMinuteBtnStatus().observe(appCompatActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mFiveMinuteBtn.setClickable(true);
                    mFiveMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                    mFiveMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                    mFiveMinuteBtn.setText("获取");
                } else {
                    mFiveMinuteBtn.setClickable(false);
                    mFiveMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.color_2E2E2E));
                    mFiveMinuteBtn.setBackgroundResource(R.drawable.bg_btn_unable);
                }

            }
        });
        mCreditStatusViewModel.getFifteenMinuteBtnStatus().observe(appCompatActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mFifteenMinuteBtn.setClickable(true);
                    mFifteenMinuteBtn.setBackgroundResource(R.drawable.ripple_btn);
                    mFifteenMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                    mFifteenMinuteBtn.setText("获取");
                } else {
                    mFifteenMinuteBtn.setClickable(false);
                    mFifteenMinuteBtn.setTextColor(mContext.getResources().getColor(R.color.color_2E2E2E));
                    mFifteenMinuteBtn.setBackgroundResource(R.drawable.bg_btn_unable);
                }
            }
        });

        mCreditStatusViewModel.getOneMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong == 0){
                    return;
                }
                mOneMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });
        mCreditStatusViewModel.getFiveMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong == 0){
                    return;
                }
                mFiveMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });
        mCreditStatusViewModel.getFifteenMinuteCountTimer().observe(appCompatActivity, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong == 0){
                    return;
                }
                mFifteenMinuteBtn.setText(ZTimeUtils.secToTime(aLong/1000));
            }
        });
    }

    private void initOnClickListener(final AppCompatActivity activity){
        mOneMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setClickable(false);
                if (CreditStatusManager.getInstance().getOneMinuteCredit()){
                    CreditStatusManager.getInstance().startOneMinuteCountTimer(mOneMinuteBtn);
                    getCredit(activity,CreditOperationManager.MIN_CREDIT_1,CreditOperationManager.MIN_CREDIT_2);
                }
            }
        });
        mFiveMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setClickable(false);
                if (CreditStatusManager.getInstance().getFiveMinuteCredit()) {
                    CreditStatusManager.getInstance().startFiveMinuteCountTimer(mFiveMinuteBtn);
                    getCredit(activity,CreditOperationManager.MIN_CREDIT_1,CreditOperationManager.MIN_CREDIT_3);
                }
            }
        });
        mFifteenMinuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setClickable(false);
                if (CreditStatusManager.getInstance().getFifteenMinuteCredit()) {
                    CreditStatusManager.getInstance().startFifteenMinuteCountTimer(mFifteenMinuteBtn);
                    getCredit(activity,CreditOperationManager.MIN_CREDIT_1,CreditOperationManager.MAX_CREDIT_5);
                }
            }
        });
    }

    public void getCredit(AppCompatActivity activity,long min , long max){
        long getCredit = CreditOperationManager.getInstance().getCredit(min,max);
        //todo 看完弹窗，再拿积分
        CreditResultDialog.showDialogNow(activity.getSupportFragmentManager(),getCredit);
    }
}
