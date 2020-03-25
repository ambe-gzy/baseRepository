package cn.zhenye.common.credit.manager;

public class CreditOperationManager {
    public static long MIN_CREDIT_1 = 1;
    public static long MIN_CREDIT_2 = 3;
    public static long MIN_CREDIT_3 = 10;
    public static long MAX_CREDIT_5 = 10;
    public static long MAX_CREDIT_6 = 6;
    public static long MAX_CREDIT_10 = 10;

    public static CreditOperationManager getInstance(){
        return getHolder.INSTANCE;
    }

    private static class getHolder{
        private static final CreditOperationManager INSTANCE = new CreditOperationManager();
    }

    public long getCredit(long min,long max){
        long getCredit = 0;
        getCredit =(long)(min + Math.random()*(max - min +1));
        getCredit = getCredit * 10;
        return getCredit;
    }

}
