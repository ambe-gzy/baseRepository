package cn.zhenye.voicereverse.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VoiceFileAdapter extends RecyclerView.Adapter<VoiceFileAdapter.VoiceFileViewHolder> {


    @NonNull
    @Override
    public VoiceFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceFileViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VoiceFileViewHolder extends RecyclerView.ViewHolder{

        public VoiceFileViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
