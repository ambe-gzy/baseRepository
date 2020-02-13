package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import cn.zhenye.base.base.BaseFragment;
import cn.zhenye.common.voicereverse.AudioRecordManager;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.fragment.viewpager.VoicePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class VoicePlayFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice_reverse_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initViewPagerAndTabLayout();
        initRecorder();
    }

    private void initViewPagerAndTabLayout() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(new VoiceAuthorFragment());
//        fragments.add(new VoiceChallengerFragment());
        titles.add(getResources().getString(R.string.fragment_voice_author));
//        titles.add(getResources().getString(R.string.fragment_voice_challenger));

        VoicePagerAdapter adapter = new VoicePagerAdapter(getParentFragmentManager(),fragments,titles);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initUI(View view){
        mViewPager = view.findViewById(R.id.fragment_voice_viewpager);
        mTabLayout = view.findViewById(R.id.fragment_voice_tab_layout);
    }

    private void initRecorder() {
        AudioRecordManager.getInstance().init(getActivity().getApplication());
    }

}
