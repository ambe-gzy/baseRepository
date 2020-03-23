package cn.zhenye.common.constants;

public class ServerResultConstants {

    // common
    public static final int IDLE = -1; // 默认状态
    public static final int ERROR_UNKNOWN = -9999; // 未知错误
    public static final int OK = 0; // 接口请求成功

    // internal error
    public static final int ERROR_REQUEST_EXCEPTION = -11;
    public static final int ERROR_CONTEXT_NULL = -12;
    public static final int ERROR_RESPONSE_NULL = -13;
    public static final int ERROR_RESPONSE_DECRYPT_ERROR = -14;
    public static final int ERROR_RESPONSE_PARSED_NULL = -15;
    public static final int ERROR_STATE_JSON_PARSED_EXCEPTION = -16;
    public static final int ERROR_STATE_NETWORK_UNAVAILABLE = -17;  // 网络不可用
    public static final int ERROR_REQUEST_TIME_OUT = -18;  // 网络超时

    // server error 1x
    public static final int ERROR_UID = 1; // ID错误：没有此账号or账号错误
    public static final int ERROR_PARAMS = 2; // 基础参数错误
    public static final int ERROR_DATABASE = 3; // 数据库或调用第三方接口出错
    public static final int ERROR_AUTHORIZATION = 4; // 接口校验出错
    public static final int ERROR_TOKEN = 5; // TOKEN错误
    public static final int EXPIRED_TOKEN = 6; // TOKEN过期
    public static final int ERROR_ALREADY = 7; // 接口已经被调用过
    public static final int ERROR_FREQUENT = 8; // 接口调用频繁
    public static final int FORBIDDEN_USER_ID = 9; // 用户账号被禁用

    // server error 2x
    public static final int ERROR_PASSWORD_ERROR = 21; // 密码错误
    public static final int ERROR_SMS_CODE_ERROR = 22; // 验证码错误
    public static final int ERROR_BLACKLIST_PHONE = 23;// 黑名单号码
    public static final int ERROR_SECURITY_CHECK = 24; // 安全检查失败
    public static final int ERROR_SMS_CODE_EXPIRED = 26; // 验证阿妈过期
    public static final int ERROR_INVALID_PHONE = 27; // 手机号码不合法
    public static final int ERROR_TODO_ERROR = 29;

    // server error 3x
    public static final int ERROR_INVITE_CODE = 31; // 邀请代码错误
    public static final int ERROR_INVITE_YOURSELF = 32; // 邀请自己
    public static final int ERROR_INVITED = 33; // 已经被邀请过了
    public static final int ERROR_NOT_BIND = 34;// 自己或者对方没有绑定手机
    public static final int ERROR_INVITE_BACK = 35;// 不可互相邀请

    // server error 4x
    public static final int ERROR_UPDATE_SAME_DATA = 42;

    // server error 6x
    public static final int ERROR_ACTIVITY_EXPIRED = 61; // 活动过期
    public static final int ERROR_ACTIVITY_NOT_REACH = 62; // 活动未达标

    // server error 7x
    public static final int ERROR_ACTIVITY_VISITOR_ACCESS_DENY = 71; // 游客拒绝访问
    public static final int ERROR_ACTIVITY_TEMPORARILY_INVALID = 72; // 广告违法，暂时不可用

    // server error 8x 短信
    public static final int ERROR_SEND_MSG_INVALID_NUM = 27; // 不支持发该号码
    public static final int ERROR_SEND_MSG_INSUFFICIENT_CREDIT = 87; // 积分不足


    public static boolean isHandledError(int errorCode) {
        // common
        if (errorCode == IDLE
                || errorCode == ERROR_UNKNOWN
                || errorCode == OK) {
            return true;
        }

        // internal error
        if (errorCode == ERROR_REQUEST_EXCEPTION
                || errorCode == ERROR_CONTEXT_NULL
                || errorCode == ERROR_RESPONSE_NULL
                || errorCode == ERROR_RESPONSE_DECRYPT_ERROR
                || errorCode == ERROR_RESPONSE_PARSED_NULL
                || errorCode == ERROR_STATE_JSON_PARSED_EXCEPTION
                || errorCode == ERROR_STATE_NETWORK_UNAVAILABLE) {
            return true;
        }

        // server error 1x
        if (errorCode == ERROR_UID
                || errorCode == ERROR_PARAMS
                || errorCode == ERROR_DATABASE
                || errorCode == ERROR_AUTHORIZATION
                || errorCode == ERROR_TOKEN
                || errorCode == EXPIRED_TOKEN
                || errorCode == ERROR_ALREADY
                || errorCode == ERROR_FREQUENT
                || errorCode == FORBIDDEN_USER_ID) {
            return true;
        }

        // server error 2x
        if (errorCode == ERROR_PASSWORD_ERROR
                || errorCode == ERROR_SMS_CODE_ERROR
                || errorCode == ERROR_INVALID_PHONE
                || errorCode == ERROR_BLACKLIST_PHONE
                || errorCode == ERROR_SECURITY_CHECK
                || errorCode == ERROR_SMS_CODE_EXPIRED
                || errorCode == ERROR_TODO_ERROR) {
            return true;
        }

        // server error 3x
        if (errorCode == ERROR_INVITE_CODE
                || errorCode == ERROR_INVITE_YOURSELF
                || errorCode == ERROR_INVITED
                || errorCode == ERROR_NOT_BIND
                || errorCode == ERROR_INVITE_BACK) {
            return true;
        }

        // server error 4x
        if (errorCode == ERROR_UPDATE_SAME_DATA){
            return true;
        }

        // server error 7x
        if (errorCode == ERROR_ACTIVITY_TEMPORARILY_INVALID) {
            return true;
        }

        return false;
    }

}
