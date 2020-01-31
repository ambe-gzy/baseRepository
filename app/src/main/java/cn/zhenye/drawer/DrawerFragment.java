package cn.zhenye.drawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.walle.WalleChannelReader;

import cn.zhenye.home.BuildConfig;
import cn.zhenye.home.R;

public class DrawerFragment extends Fragment {

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAppVersion(view);
    }

    private void initAppVersion(View view){
        TextView appVersion = view.findViewById(R.id.tv_app_version);
        appVersion.setText(getResources().getString(R.string.app_version, WalleChannelReader.getChannel(getContext()), BuildConfig.BUILD_TYPE,BuildConfig.VERSION_NAME));
    }
}
