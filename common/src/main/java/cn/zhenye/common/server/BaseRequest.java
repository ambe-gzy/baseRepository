package cn.zhenye.common.server;

import com.google.gson.annotations.SerializedName;

public class BaseRequest {

    @SerializedName("did")
    private String mDid = null;

    @SerializedName("cnl")
    private String mCnl = null;

    @SerializedName("mcc")
    private String mMcc = null;

    @SerializedName("mnc")
    private String mMnc = null;

    @SerializedName("pkg")
    private String mPkg = null;

    @SerializedName("lang")
    private String mLang = null;

    @SerializedName("cv")
    private String mCv = null;

    @SerializedName("ts")
    private String mTs = null;

    @SerializedName("uid")
    private String mUid = null;

    @SerializedName("token")
    private String mToken = null;

    @SerializedName("is_rooted")
    private boolean mIsRooted = false;

    @SerializedName("is_vpn_used")
    private boolean mIsVPNUsed = false;

    public String getDid() {
        return mDid;
    }

    public void setDid(String did) {
        mDid = did;
    }

    public String getCnl() {
        return mCnl;
    }

    public void setCnl(String cnl) {
        mCnl = cnl;
    }

    public String getMcc() {
        return mMcc;
    }

    public void setMcc(String mcc) {
        mMcc = mcc;
    }

    public String getMnc() {
        return mMnc;
    }

    public void setMnc(String mnc) {
        mMnc = mnc;
    }

    public String getPkg() {
        return mPkg;
    }

    public void setPkg(String pkg) {
        mPkg = pkg;
    }

    public String getLang() {
        return mLang;
    }

    public void setLang(String lang) {
        mLang = lang;
    }

    public String getCv() {
        return mCv;
    }

    public void setCv(String cv) {
        mCv = cv;
    }

    public String getTs() {
        return mTs;
    }

    public void setTs(String ts) {
        mTs = ts;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public boolean isRooted() {
        return mIsRooted;
    }

    public void setIsRooted(boolean isRooted) {
        mIsRooted = isRooted;
    }

    public boolean isVPNUsed() {
        return mIsVPNUsed;
    }

    public void setIsVPNUsed(boolean isVPNUsed) {
        mIsVPNUsed = isVPNUsed;
    }

}
