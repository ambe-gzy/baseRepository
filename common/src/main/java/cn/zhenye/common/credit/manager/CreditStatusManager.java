package cn.zhenye.common.credit.manager;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.base.timer.CountDownTask;
import cn.zhenye.base.timer.CountDownTimers;
import cn.zhenye.common.constants.CreditConstants;

import java.util.ArrayList;

/**
 * @author zhenye on 20191230
 * 判断获取积分按钮能否点击
 * 判断获取积分按钮下次点击剩余时间
 */
public class CreditStatusManager {

    public static CreditStatusManager getInstance() {
        return Holder.INSTANCE;
    }
    private static class Holder {
        private static final CreditStatusManager INSTANCE = new CreditStatusManager();
    }

    private CreditStatusManager(){
        Intent intent = new Intent();
    }

    //是否允许获取积分
    private ArrayList<OnRefreshListener<Boolean>> mOneMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFiveMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFifteenMinuteBtnStatusListener = new ArrayList<>();

    //下次获取积分倒计时
    private ArrayList<OnRefreshListener<Long>> mOneMinuteBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Long>> mFiveMinuteBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Long>> mFifteenMinuteBtnCountTimerListener = new ArrayList<>();

    //开始倒计时
    public void startOneMinuteCountTimer(long elapsedRealtime, View view){
        CountDownTask.create().until(view, elapsedRealtime, 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyOneMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyOneMinuteStatusCallBack(true);
                notifyOneMinuteCountTimerCallback((long) 0);
            }
        });
    }
    public void startFiveMinuteCountTimer(long elapsedRealtime ,View view){
        CountDownTask.create().until(view, elapsedRealtime, 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyFiveMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyFiveMinuteStatusCallBack(true);
                notifyFiveMinuteCountTimerCallback((long) 0);
            }
        });
    }
    public void startFifteenMinuteCountTimer(long elapsedRealtime,View view){
        CountDownTask.create().until(view, elapsedRealtime, 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyFifteenMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyFifteenMinuteStatusCallBack(true);
                notifyFifteenMinuteCountTimerCallback((long) 0);
            }
        });
    }

    public void startOneMinuteCountTimer(View view) {
        CountDownTask.create().until(view, getOneMinuteCacheRemain(), 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyOneMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyOneMinuteStatusCallBack(true);
                notifyOneMinuteCountTimerCallback((long) 0);
            }
        });
    }
    public void startFiveMinuteCountTimer(View view) {
        CountDownTask.create().until(view, getFiveMinuteCacheRemain(), 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyFiveMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyFiveMinuteStatusCallBack(true);
                notifyFiveMinuteCountTimerCallback((long) 0);
            }
        });
    }
    public void startFifteenMinuteCountTimer(View view) {
        CountDownTask.create().until(view, getFifteenMinuteCacheRemain(), 1000, new CountDownTimers.OnCountDownListener() {
            @Override
            public void onTick(View view, long millisUntilFinished) {
                notifyFifteenMinuteCountTimerCallback(millisUntilFinished);
            }

            @Override
            public void onFinish(View view) {
                notifyFifteenMinuteStatusCallBack(true);
                notifyFifteenMinuteCountTimerCallback((long) 0);
            }
        });
    }

    public void notifyStatus(){
        getOneMinuteCacheRemain();
        getFiveMinuteCacheRemain();
        getFifteenMinuteCacheRemain();
    }

    //读取本地保存的数据
    public long getOneMinuteCacheRemain(){
        long remain = ZyCacheStorage.getLong(CreditConstants.ONE_MINUTE_REMAIN,0);
        long isFinish = remain - SystemClock.elapsedRealtime();
        if (isFinish > 0){
            notifyOneMinuteStatusCallBack(false);
            return remain;
        }else {
            notifyOneMinuteStatusCallBack(true);
            return 0;
        }

    }
    public long getFiveMinuteCacheRemain(){
        long remain = ZyCacheStorage.getLong(CreditConstants.FIVE_MINUTE_REMAIN,0);
        long isFinish = remain - SystemClock.elapsedRealtime();
        if (isFinish > 0){
            notifyFiveMinuteStatusCallBack(false);
            return remain;
        }else {
            notifyFiveMinuteStatusCallBack(true);
            return 0;
        }
    }
    public long getFifteenMinuteCacheRemain(){
        long remain = ZyCacheStorage.getLong(CreditConstants.FIFTEEN_MINUTE_REMAIN,0);
        long isFinish = remain - SystemClock.elapsedRealtime();
        if (isFinish > 0){
            notifyFifteenMinuteStatusCallBack(false);
            return remain;
        }else {
            notifyFifteenMinuteStatusCallBack(true);
            return 0;
        }
    }
    
    //状态监听者 注册、移除
    public void registerOneMinuteBtnStatusListener(OnRefreshListener<Boolean> listener){
        mOneMinuteBtnStatusListener.add(listener);
    }
    public void regiserFiveMinuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mFiveMinuteBtnStatusListener.add(listener);
    }
    public void regiserFifteenMinuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mFifteenMinuteBtnStatusListener.add(listener);
    }
    public void removeOneMinuteBtnStatusListener(OnRefreshListener<Boolean> listener){
        mOneMinuteBtnStatusListener.remove(listener);
    }
    public void removeFiveMinuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mFiveMinuteBtnStatusListener.remove(listener);
    }
    public void removeFifteenMinuteBtnStatusListener(OnRefreshListener<Boolean> listener) {
        mFifteenMinuteBtnStatusListener.remove(listener);
    }

    //倒计时监听者 注册、移除
    public void registerOneMinuteCountTimerListener(OnRefreshListener<Long> listener){
        mOneMinuteBtnCountTimerListener.add(listener);
    }
    public void registerFiveMinuteCountTimerListener(OnRefreshListener<Long> listener) {
        mFiveMinuteBtnCountTimerListener.add(listener);
    }
    public void registerFifteenMinuteCountTimerListener(OnRefreshListener<Long> listener) {
        mFifteenMinuteBtnCountTimerListener.add(listener);
    }
    public void removeOneMinuteCountTimerListener(OnRefreshListener<Long> listener){
        mOneMinuteBtnCountTimerListener.remove(listener);
    }
    public void removeFiveMinuteCountTimerListener(OnRefreshListener<Long> listener){
        mFiveMinuteBtnCountTimerListener.remove(listener);
    }
    public void removeFifteenMinuteCountTimerListener(OnRefreshListener<Long> listener){
        mFifteenMinuteBtnCountTimerListener.remove(listener);
    }
    
    //更新
    public void notifyOneMinuteCountTimerCallback(Long res){
        for (int i = 0;i<mOneMinuteBtnCountTimerListener.size();i++) {
            mOneMinuteBtnCountTimerListener.get(i).onRefresh(res);
        }
    }
    public void notifyFiveMinuteCountTimerCallback(Long res){
        for (int i = 0;i<mFiveMinuteBtnCountTimerListener.size();i++) {
            mFiveMinuteBtnCountTimerListener.get(i).onRefresh(res);
        }
    }
    public void notifyFifteenMinuteCountTimerCallback(Long res){
        for (int i = 0;i<mFifteenMinuteBtnCountTimerListener.size();i++) {
            mFifteenMinuteBtnCountTimerListener.get(i).onRefresh(res);
        }
    }
    public void notifyOneMinuteStatusCallBack(Boolean status){
        for (int i = 0 ;i<mOneMinuteBtnStatusListener.size();i++){
            mOneMinuteBtnStatusListener.get(i).onRefresh(status);
        }
    }
    public void notifyFiveMinuteStatusCallBack(Boolean status){
        for (int i =0 ; i<mFiveMinuteBtnStatusListener.size();i++){
            mFiveMinuteBtnStatusListener.get(i).onRefresh(status);
        }
    }
    public void notifyFifteenMinuteStatusCallBack(Boolean status){
        for (int i = 0;i<mFifteenMinuteBtnStatusListener.size();i++){
            mFifteenMinuteBtnStatusListener.get(i).onRefresh(status);
        }
    }

    //尝试获取积分
    public boolean getOneMinuteCredit(){
        if (getOneMinuteCacheRemain() == 0){
            long nextTime = SystemClock.elapsedRealtime() + CreditConstants.ONE_MINUTE_TIME;
            ZyCacheStorage.put(CreditConstants.ONE_MINUTE_REMAIN,nextTime);
            return true;
        }else {
            return false;
        }
    }
    public boolean getFiveMinuteCredit(){
        if (getFiveMinuteCacheRemain() == 0){
            long nextTime = SystemClock.elapsedRealtime() + CreditConstants.FIVE_MINUTE_TIME;
            ZyCacheStorage.put(CreditConstants.FIVE_MINUTE_REMAIN,nextTime);
            return true;
        }else {
            return false;
        }

    }
    public boolean getFifteenMinuteCredit(){
        if (getFifteenMinuteCacheRemain() == 0) {
            long nextTime = SystemClock.elapsedRealtime() + CreditConstants.FIFTEEN_MINUTE_TIME;
            ZyCacheStorage.put(CreditConstants.FIFTEEN_MINUTE_REMAIN,nextTime);
            return true;
        } else {
            return false;
        }
    }
    
    public interface OnRefreshListener<TResult>{
        void onRefresh(@NonNull TResult task);
    }

}
