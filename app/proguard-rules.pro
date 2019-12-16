#关闭压缩,默认开启，用以减小应用体积，移除未被使用的类和成员
#-dontshrink

#默认开启，在字节码级别执行优化，让应用运行的更快。
#-dontoptimize  关闭优化
#-optimizationpasses n 表示proguard对代码进行迭代优化的次数，Android一般为5

#友盟
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class cn.zhenye.partytool.R$*{
public static final int *;
}
#混淆（Obfuscation）：默认开启，增大反编译难度，类和类成员会被随机命名，除非用keep保护。
#-dontobfuscate 关闭混淆