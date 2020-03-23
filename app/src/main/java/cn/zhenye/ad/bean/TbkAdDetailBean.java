package cn.zhenye.ad.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TbkAdDetailBean implements Parcelable {

    @SerializedName("title")
    public String title;

    public List<String> pic_urls;

    @SerializedName("coupon_click_url")
    public String couponClickUrl;

    public TbkAdDetailBean() {
    }

    protected TbkAdDetailBean(Parcel in) {
        title = in.readString();
        pic_urls = in.createStringArrayList();
        couponClickUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(pic_urls);
        dest.writeString(couponClickUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TbkAdDetailBean> CREATOR = new Creator<TbkAdDetailBean>() {
        @Override
        public TbkAdDetailBean createFromParcel(Parcel in) {
            return new TbkAdDetailBean(in);
        }

        @Override
        public TbkAdDetailBean[] newArray(int size) {
            return new TbkAdDetailBean[size];
        }
    };
}
