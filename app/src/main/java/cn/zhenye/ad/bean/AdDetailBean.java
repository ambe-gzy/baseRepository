package cn.zhenye.ad.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AdDetailBean implements Parcelable {

    @SerializedName("title")
    public String title;

    @SerializedName("taokouling")
    public String tkl;

    @SerializedName("goods_id")
    public String num_iid;

    public AdDetailBean(){}

    protected AdDetailBean(Parcel in) {
        title = in.readString();
        tkl = in.readString();
        num_iid = in.readString();
    }

    public static final Creator<AdDetailBean> CREATOR = new Creator<AdDetailBean>() {
        @Override
        public AdDetailBean createFromParcel(Parcel in) {
            return new AdDetailBean(in);
        }

        @Override
        public AdDetailBean[] newArray(int size) {
            return new AdDetailBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(tkl);
        dest.writeString(num_iid);
    }
}
