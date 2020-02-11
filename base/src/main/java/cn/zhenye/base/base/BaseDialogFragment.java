package cn.zhenye.base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 重写show和dismiss方法，防止Fragment already added错误。
 * 所有DialogFragment都需要继承此类
 *
 * @author WilliamChik on 2018/10/17.
 */
public class BaseDialogFragment extends DialogFragment {
    public BaseDialogFragment() {
        super();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (this.isAdded()) {
            return -1;
        }

        try {
            return super.show(transaction, tag);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return -1;
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (this.isAdded()) {
            return;
        }

        try {
            super.show(manager, tag);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void showNow(FragmentManager manager, String tag) {
        if (this.isAdded()) {
            return;
        }

        try {
            super.showNow(manager, tag);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
