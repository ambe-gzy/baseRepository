package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mintegral.msdk.base.fragment.BaseFragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.vm.VoiceViewModel;


public class VoiceRecordFragment extends BaseFragment {
    private VoiceViewModel mVoiceViewModel;
    private static String TAG = VoiceRecordFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_reverse_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVM();
    }

    private void initVM() {
        mVoiceViewModel =  ViewModelProviders.of(getActivity()).get(VoiceViewModel.class);
        mVoiceViewModel.getCurrentFilePah().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG,"save path is: "+s);
            }
        });
    }
}
