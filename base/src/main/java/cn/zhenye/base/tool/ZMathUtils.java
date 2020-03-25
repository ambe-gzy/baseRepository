package cn.zhenye.base.tool;

public class ZMathUtils {

    public static int getRandom(int min,int max){
        int count = 0;
        count =(int) (min + Math.random()*(max - min +1));
        return count;
    }
}
