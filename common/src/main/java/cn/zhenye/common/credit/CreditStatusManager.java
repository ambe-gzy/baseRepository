package cn.zhenye.common.credit;

import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author zhenye on 20191230
 * 判断获取积分按钮能否点击
 * 判断获取积分按钮下次点击剩余时间
 */
public class CreditStatusManager {
    private static CreditStatusManager INSTANCE;
    public class Holder {
        CreditStatusManager getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new CreditStatusManager();
            }
            return INSTANCE;
        }
    }

    private CreditStatusManager(){
        Intent intent = new Intent();
    }

    //是否允许获取积分
    private ArrayList<OnRefreshListener<Boolean>> mOneMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFiveMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFifteenMinuteBtnStatusListener = new ArrayList<>();

    //下次获取积分倒计时
    private ArrayList<OnRefreshListener<Integer>> mOneMinuteBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Integer>> mFiveMinuteBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Integer>> mFifteenMinuteBtnCountTimerListener = new ArrayList<>();

//    public void setOn




    public interface OnRefreshListener<TResult>{
        void onRefresh(@NonNull TResult task);
    }


}
