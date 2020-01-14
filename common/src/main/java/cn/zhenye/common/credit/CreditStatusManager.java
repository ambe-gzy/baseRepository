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

    public void init(){
//        long timeStamp =
    }


//积分点击状态
    private void registerOneMunuteBtnStatusListener(OnRefreshListener<Boolean> listener){
        mOneMinuteBtnStatusListener.add(listener);
    }
    private void registerFiveMunuteBtnStatusListener(OnRefreshListener<Boolean> listener){
        mFiveMinuteBtnStatusListener.add(listener);
    }
    private void registerFifteemMunuteBtnStatusListener(OnRefreshListener<Boolean> listener){
        mFifteenMinuteBtnStatusListener.add(listener);
    }

    private void removeOneMunuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mOneMinuteBtnStatusListener.remove(listener);
    }
    private void removeFiveMunuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mOneMinuteBtnStatusListener.remove(listener);
    }
    private void removeFifteenMunuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mOneMinuteBtnStatusListener.remove(listener);
    }

//积分下次点击剩余时间
    private void registerOneMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mOneMinuteBtnCountTimerListener.add(listener);
    }
    private void registerFiveMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mFiveMinuteBtnCountTimerListener.add(listener);
    }
    private void registerFifteenMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mFifteenMinuteBtnCountTimerListener.add(listener);
    }

    private void removeOneMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mOneMinuteBtnCountTimerListener.remove(listener);
    }
    private void removeFiveMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mFiveMinuteBtnCountTimerListener.remove(listener);
    }
    private void removeFifteenMinuteBtnCountTimerListener(OnRefreshListener<Integer> listener){
        mFifteenMinuteBtnCountTimerListener.remove(listener);
    }



    public interface OnRefreshListener<TResult>{
        void onRefresh(@NonNull TResult task);
    }


}
