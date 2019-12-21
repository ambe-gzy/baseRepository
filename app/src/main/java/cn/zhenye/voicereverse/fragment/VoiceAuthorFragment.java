package cn.zhenye.voicereverse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import cn.zhenye.main.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class VoiceAuthorFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_auth, container, false);
    }


}
