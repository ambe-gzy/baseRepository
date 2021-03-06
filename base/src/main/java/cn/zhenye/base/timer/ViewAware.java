package cn.zhenye.base.timer;

import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author zhaibinme on 2018/8/25
 */
class ViewAware {
    private static final String TAG = "ViewAware";

    protected Reference<View> mViewRef;

    public ViewAware(View view) {
        if (view == null) throw new IllegalArgumentException("view must not be null");
        mViewRef = new WeakReference<View>(view);
    }

    public View getWrappedView() {
        return mViewRef.get();
    }

    public boolean isCollected() {
        return mViewRef.get() == null;
    }

    public int getId() {
        View view = mViewRef.get();
        return view == null ? super.hashCode() : view.hashCode();
    }

}
