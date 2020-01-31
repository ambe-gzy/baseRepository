package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mintegral.msdk.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.adapter.VoiceFileAdapter;
import cn.zhenye.voicereverse.fragment.adapter.VoiceRecordAdapter;
import cn.zhenye.voicereverse.vm.VoiceViewModel;


public class VoiceRecordFragment extends BaseFragment {
    private VoiceViewModel mVoiceViewModel;
    private static String TAG = VoiceRecordFragment.class.getName();
    private VoiceRecordAdapter mVoiceRecordAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_reverse_record, container, false);
    }
;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initVM();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.rv_voice_record);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mVoiceRecordAdapter = new VoiceRecordAdapter();
        recyclerView.setAdapter(mVoiceRecordAdapter);
    }

    private void initVM() {
        mVoiceViewModel =  ViewModelProviders.of(getActivity()).get(VoiceViewModel.class);
        mVoiceViewModel.getCurrentFilePah().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG,"save path is: "+s);
            }
        });
        mVoiceViewModel.getVoiceEntityLiveData().observe(getActivity(), new Observer<List<VoiceEntity>>() {
            @Override
            public void onChanged(List<VoiceEntity> voiceEntities) {
                List<VoiceEntity> currentEntity = getCurrentList(voiceEntities);
                mVoiceRecordAdapter.setList(currentEntity);
            }
        });
    }

    private List<VoiceEntity> getCurrentList(List<VoiceEntity> voiceEntities){
        String currentPath = mVoiceViewModel.getCurrentFilePah().getValue();
        if (TextUtils.isEmpty(currentPath)){
            return voiceEntities;
        }
        List<VoiceEntity> entities = new ArrayList<>();

        for (int i = 0;i<voiceEntities.size();i++) {
            if (voiceEntities.get(i).savePath.equals(currentPath)){
                entities.add(voiceEntities.get(i));
            }
        }
        return entities;
    }
}
