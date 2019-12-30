package cn.zhenye.common.credit;

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

    private ArrayList<OnRefreshListener<Boolean>> mOneMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFiveMinuteBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Boolean>> mFifteenBtnStatusListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Integer>> mOneMinuteBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Integer>> mOneFiveBtnCountTimerListener = new ArrayList<>();
    private ArrayList<OnRefreshListener<Integer>> mOneFifteenBtnCountTimerListener = new ArrayList<>();

//    public void setOn


    public interface OnRefreshListener<TResult>{
        void onRefresh(@NonNull TResult task);
    }
}
