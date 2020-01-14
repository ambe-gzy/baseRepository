package cn.zhenye.voicereverse.fragment.adapter;

import android.content.Context;
import android.media.TimedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.AudioPlayManager;
import cn.zhenye.common.voicereverse.OnAudioPlayListener;
import cn.zhenye.home.R;

public class VoiceRecordAdapter extends RecyclerView.Adapter<VoiceRecordAdapter.VoiceHolder>{
    private List<VoiceEntity> mList = new ArrayList<>();
    private Context mAppContext;

    @NonNull
    @Override
    public VoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mAppContext == null){
            mAppContext = parent.getContext().getApplicationContext();
        }
        return new VoiceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice_record,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceHolder holder, int position) {
        VoiceEntity entity = mList.get(position);
        holder.name.setText(entity.saveName);
        setItemListener(holder,entity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VoiceHolder extends RecyclerView.ViewHolder {
        View btnNormalPlay;
        View btnNormalReverse;
        View btnChallangePlay;
        View btnChallengeReverse;
        TextView name;


        public VoiceHolder(@NonNull View itemView) {
            super(itemView);
            btnNormalPlay = itemView.findViewById(R.id.btn_normal_play);
            btnNormalReverse = itemView.findViewById(R.id.btn_normal_reverse);
            btnChallangePlay = itemView.findViewById(R.id.btn_challenge_play);
            btnChallengeReverse = itemView.findViewById(R.id.btn_challenge_reverse);
            name = itemView.findViewById(R.id.tv_item_record_name);
        }
    }

    public void setList(List<VoiceEntity> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    private void setItemListener(final VoiceHolder holder , final VoiceEntity entity){
        final String saveName = entity.saveName;
        final TextView name = holder.name;
        holder.btnNormalPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(entity.normalVoicePath,view,name,saveName);

            }
        });
        holder.btnNormalReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(entity.normalReverseVoicePath,view,name,saveName);
            }
        });
        holder.btnChallangePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(entity.answerVoicePath,view,name,saveName);
            }
        });
        holder.btnChallengeReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(entity.answerReverseVoicePath,view,name,saveName);
            }
        });
    }

    private void playAudio(String path, View btn, final TextView name, final String nameStr){
        if (AudioPlayManager.getInstance().isPlaying()) {
            ZToastUtils.showShort("正在播放，请稍后");
            return;
        }
        AudioPlayManager.getInstance().play(path, (TextView) btn, new OnAudioPlayListener() {
            @Override
            public void audioStart(TextView btn) {
                name.setText("播放中");
                name.setTextColor(mAppContext.getResources().getColor(R.color.color_A6A0C6));
            }

            @Override
            public void audioPlaying(TimedText timedText) {

            }

            @Override
            public void audioStop(TextView btn) {
                name.setText(nameStr);
                name.setTextColor(mAppContext.getResources().getColor(R.color.color_3C3885));
            }

            @Override
            public void audioPlayError(String message) {

            }
        });
    }
}
