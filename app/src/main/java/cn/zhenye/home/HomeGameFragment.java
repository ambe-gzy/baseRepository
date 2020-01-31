package cn.zhenye.home;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.voicereverse.VoiceFileActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meituan.android.walle.WalleChannelReader;
import com.mintegral.msdk.base.fragment.BaseFragment;

public class HomeGameFragment extends BaseFragment implements View.OnClickListener {
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

//        channel.setText(WalleChannelReader.getChannel(getActivity().getApplicationContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_main_voice_reverse:
                ZActivityUtils.safeStartActivityWithActivity(getActivity(), VoiceFileActivity.class);
                break;
            case R.id.iv_main_guide:
                //todo 显示如何玩；
                break;
        }
    }
}
