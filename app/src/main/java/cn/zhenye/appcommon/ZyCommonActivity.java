package cn.zhenye.appcommon;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.base.base.BaseFullScreenActivity;
import cn.zhenye.common.credit.VM.CreditStatusViewModel;
import cn.zhenye.common.credit.VM.CreditViewModel;

/**
 * 在此配置基础方法
 */
public abstract class ZyCommonActivity extends BaseFullScreenActivity {

    private CreditStatusViewModel mCreditStatusViewModel;
    private CreditViewModel mCreditViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(getApplicationContext());
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(getApplicationContext());
        super.onPause();
    }

    private void initViewModel(){
        mCreditStatusViewModel = ViewModelProviders.of(this).get(CreditStatusViewModel.class);
        mCreditViewModel = ViewModelProviders.of(this).get(CreditViewModel.class);
        mCreditViewModel.getCurrentCreditLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setCurrentCredit(s);
            }
        });
    }

    public CreditStatusViewModel getCreditStatusViewModel(){
        return mCreditStatusViewModel;
    }



}
