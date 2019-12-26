package cn.zhenye.voicereverse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.base.tool.TimeUtil;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.home.R;

public class VoiceFileAdapter extends RecyclerView.Adapter<VoiceFileAdapter.VoiceFileViewHolder> {
    private List<VoiceFileEntity> voiceFileEntities;
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public VoiceFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoiceFileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voice_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VoiceFileViewHolder holder, int position) {
        if (voiceFileEntities == null || voiceFileEntities.size() <= position) {
            return;
        }
        final VoiceFileEntity voiceFileEntity = voiceFileEntities.get(position);
        holder.fileName.setText(voiceFileEntity.fileName);
        holder.fileCreateTime.setText(TimeUtil.getDateToString(voiceFileEntity.createTimestamp, TimeUtil.ACCURATE_TO_S));
        holder.fileNum.setText(String.valueOf(voiceFileEntity.fileNum));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener !=null){
                    mOnItemClickListener.onClick(voiceFileEntity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return voiceFileEntities == null ? 0 : voiceFileEntities.size();
    }

    class VoiceFileViewHolder extends RecyclerView.ViewHolder {
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

    public void refreshAdapter(List<VoiceFileEntity> voiceFileEntities) {
        this.voiceFileEntities = voiceFileEntities;
        notifyDataSetChanged();
    }

    public void setListenr(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(VoiceFileEntity entity);
    }
}
