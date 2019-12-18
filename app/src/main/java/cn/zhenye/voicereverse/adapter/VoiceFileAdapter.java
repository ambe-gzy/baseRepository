package cn.zhenye.voicereverse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.main.R;

public class VoiceFileAdapter extends RecyclerView.Adapter<VoiceFileAdapter.VoiceFileViewHolder> {
    private List<VoiceFileEntity> voiceFileEntities;

    @NonNull
    @Override
    public VoiceFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoiceFileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice_file,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceFileViewHolder holder, int position) {
        if (voiceFileEntities == null || voiceFileEntities.size()<=position){
            return;
        }
        VoiceFileEntity voiceFileEntity = voiceFileEntities.get(position);
//        String createTime =
        holder.fileName.setText(voiceFileEntity.fileName);
//        holder.fileCreateTime.setText(voiceFileEntity.createTimestamp);
        holder.fileNum.setText(String.valueOf(voiceFileEntity.fileNum));
    }

    @Override
    public int getItemCount() {
        return voiceFileEntities == null?0:voiceFileEntities.size();
    }

    class VoiceFileViewHolder extends RecyclerView.ViewHolder{
        TextView fileName;
        TextView fileNum;
        TextView fileCreateTime;

        VoiceFileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.tv_voice_file_name);
            fileNum = itemView.findViewById(R.id.tv_voice_file_contain_num);
            fileCreateTime = itemView.findViewById(R.id.tv_voice_file_create_time);
        }
    }

    public void refreshAdapter(List<VoiceFileEntity> voiceFileEntities){
        this.voiceFileEntities = voiceFileEntities;
        notifyDataSetChanged();
    }
}
