package cn.zhenye.common.credit.manager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.common.constants.CreditConstants;

public class CreditManager {
    private ArrayList<OnRefreshListener<String>> mCurrentCreditListener = new ArrayList<>();
    public static CreditManager getInstance(){
        return getHolder.INSTANCE;
    }

    private static class getHolder {
        private static final CreditManager INSTANCE = new CreditManager();
    }

    public void registerCurrentCreditListener(OnRefreshListener<String> creditListener){
        mCurrentCreditListener.add(creditListener);
    }
    public void removeCurrentCreditListener(OnRefreshListener<String> creditListener){
        mCurrentCreditListener.remove(creditListener);
    }

    public void notifyCurrentCreditListener(){
        long currentCredit = ZyCacheStorage.getLong(CreditConstants.CURRENT_REMAIN_CREDIT,1000);
        String currentCreditStr = String.valueOf(currentCredit);
        for (int i = 0;i<mCurrentCreditListener.size();i++) {
            mCurrentCreditListener.get(i).onRefresh(currentCreditStr);
        }
    }

    public void increaseCredit(long increaseCreditNum){
        long currentCredit = ZyCacheStorage.getLong(CreditConstants.CURRENT_REMAIN_CREDIT,1000);
        long restCredit = currentCredit + increaseCreditNum;
        ZyCacheStorage.put(CreditConstants.CURRENT_REMAIN_CREDIT,restCredit);
        notifyCurrentCreditListener();

    }

    public boolean decreaseCredit(long increaseCreditNum){
        long currentCredit = ZyCacheStorage.getLong(CreditConstants.CURRENT_REMAIN_CREDIT,1000);
        long restCredit = currentCredit - increaseCreditNum;
        if (restCredit<0){
            return false;
        }
        ZyCacheStorage.put(CreditConstants.CURRENT_REMAIN_CREDIT,restCredit);
        notifyCurrentCreditListener();
        return true;
    }

    public interface OnRefreshListener<TResult>{
        void onRefresh(@NonNull TResult task);
    }

}
