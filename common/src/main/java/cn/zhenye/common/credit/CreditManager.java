package cn.zhenye.common.credit;

import androidx.lifecycle.MutableLiveData;

public class CreditManager {
    private static CreditManager INSTANCE;
    public class getHolder {
        public CreditManager getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new CreditManager();
            }
            return INSTANCE;
        }
    }





}
