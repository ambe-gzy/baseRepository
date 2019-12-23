package cn.zhenye.main;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.voicereverse.VoiceFileActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meituan.android.walle.WalleChannelReader;

public class HomeGameFragment extends Fragment implements View.OnClickListener {
    private LinearLayout mVoiceReverseBtn;
    private ImageView mIvGuide;
    private TextView channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI(){
        mVoiceReverseBtn = getView().findViewById(R.id.ll_main_voice_reverse);
        mIvGuide = getView().findViewById(R.id.iv_main_guide);
        mIvGuide.setColorFilter(getResources().getColor(R.color.color_3C3885), PorterDuff.Mode.SRC_IN);
        mVoiceReverseBtn.setOnClickListener(this);
        mIvGuide.setOnClickListener(this);

        channel = getView().findViewById(R.id.channel);
        channel.setText(WalleChannelReader.getChannel(getActivity().getApplicationContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_main_voice_reverse:
                ActivityUtil.safeStartActivityWithActivity(getActivity(), VoiceFileActivity.class);
                break;
            case R.id.iv_main_guide:
                //todo 显示如何玩；
                break;
        }
    }
}
